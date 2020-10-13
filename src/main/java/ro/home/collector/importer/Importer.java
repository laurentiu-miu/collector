package ro.home.collector.importer;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.home.collector.model.AccountsDto;
import ro.home.collector.model.ComposedPrimaryKey;
import ro.home.collector.model.TransactionsDto;
import ro.home.collector.model.UsersDto;
import ro.home.collector.repository.AccountsRepository;
import ro.home.collector.repository.TransactionsRepository;
import ro.home.collector.repository.UsersRepository;

/**
 * Created by LaurentiuM on 29/09/2020.
 * This is where all the magic happens part of the application where we load
 * the data from the provider and save it to the DB.
 */
@Service
@Log4j2
@EnableScheduling
public class Importer implements ApplicationRunner {
  private static ImporterSettings importerSettings;
  private static UsersRepository usersRepository;
  private static AccountsRepository accountsRepository;
  private static TransactionsRepository transactionsRepository;
  private static WebClient webClient;

  public Importer(ImporterSettings importerSettings, UsersRepository usersRepository,
      AccountsRepository accountsRepository, TransactionsRepository transactionsRepository) {
    this.importerSettings = importerSettings;
    this.usersRepository = usersRepository;
    this.accountsRepository = accountsRepository;
    this.transactionsRepository = transactionsRepository;
    this.webClient = WebClient.create(importerSettings.getBaseUrl());
  }

  /**
   * here we call the provider for token
   * @param user enrich the user with token
   * @return return a cloned user enriched wit the token
   */
  public Mono<UsersDto> getJWTForUser(final UsersDto user) {
    log.info("getJWTForUser user:{}",user.getUsername());
    return webClient
        .post()
        .uri(importerSettings.getLoginUri())
        .header("username", user.getUsername())
        .retrieve()
        .bodyToMono(JsonNode.class)
        .doOnError(log::error)
        .map(json -> UsersDto.builder().username(user.getUsername())
            .jwt(json.get("token").toString())
            .importDate(LocalDate.now()).build());
  }

  /**
   * here we call the provider for accounts, if everything is ok we save them to the DB
   * @param user for which we save the accounts
   * @return a flux of saved accounts
   */
  public Flux<AccountsDto> getAndSaveAccounts(final UsersDto user) {
     return webClient.get()
        .uri(importerSettings.getAccountsUri())
        .header("X-AUTH", user.getJwt())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<AccountsDto>>() {
        })
        .doOnError(log::error)
        .map(list -> Importer.mapper(list, user, AccountsDto.class))
        .map(list -> accountsRepository.saveAll(list))
        .flatMapMany(many->many);
  }

  /**
   * here we call the provider for transactions, if everything is ok we save them to the DB
   * @param user for which we save the transactions
   * @return a flux of saved transactions
   */
  public Flux<TransactionsDto> getAndSaveTransactions(final UsersDto user) {
    return webClient.get()
        .uri(importerSettings.getTransactionsUri())
        .header("X-AUTH", user.getJwt())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<TransactionsDto>>() {
        })
        .doOnError(log::error)
        .map(list -> Importer.mapper(list, user, TransactionsDto.class))
        .map(list -> {
          if(user.getUsername().equals("a4")) throw new RuntimeException(user.getUsername());
          return transactionsRepository.saveAll(list);
        })
        .flatMapMany(many->many);
  }

  /**
   * updates the user after all data was loaded to the database, I mean accounts and transactions
   * @param user
   * @return saved user
   */
  public Mono<UsersDto> updateImportedUser(UsersDto user) {
    log.info("user {} {} {}", user.getUsername(), UsersDto.class, 1);
    user.setImportDate(LocalDate.now());
    return usersRepository.save(user);
  }

  /**
   * we need to enhance the data for cassandra, we add partition key and clustering key
   *
   * @param items list of accounts or transactions
   * @param user the user
   * @param <T> Accounts or Transactions
   * @return the list with enhanced data for cassandra
   */
  public static <T extends ComposedPrimaryKey> List<T> mapper(final List<T> items,
      final UsersDto user, final Class clazz) {
    log.info("user {} {} {}",user.getUsername(), clazz, items.size());
    return items.stream().map(item -> {
      item.setUsername(user.getUsername());
      item.setImportDate(user.getImportDate());
      return (T) item;
    }).collect(Collectors.toList());
  }

  /**
   * for a list of users we verify if the user data was imported and if not
   * we get the token send him down the pipe and get accounts and save them
   * after that get the transactions and save them and after that we update the
   * imported date
   * @param fluxOfUsers a list of users
   */
  public void importFluxOfUsers(Flux<UsersDto> fluxOfUsers){
    final long start = System.nanoTime();
      fluxOfUsers
        .filter(UserSelector::wasNotUpdatedToday)
        .flatMap(userWithJwt->getJWTForUser(userWithJwt))
        .concatMap(user->Flux.empty()
            .thenMany(getAndSaveAccounts(user))
            .thenMany(getAndSaveTransactions(user))
            .thenMany(updateImportedUser(user))
            .onErrorResume(Exception.class,ex ->{
              log.error("Exception {}", ex.getMessage());
              return Mono.empty();
            }))
         .doOnComplete(()->log.info("Time taken : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " milliseconds."))
         .subscribe();

  }

  /**
   * if we have a new user we need to do a initial import of accounts and transactions
   * @param usersDto the new user
   * @return saved users
   */
  public Mono<UsersDto> importSingleUser(UsersDto usersDto){
    return Mono.just(usersDto)
        .filter(UserSelector::wasNotUpdatedToday)
        .flatMap(user->getJWTForUser(user))
        .delayUntil(user->Flux.empty()
            .thenMany(getAndSaveAccounts(user))
            .thenMany(getAndSaveTransactions(user))
            .thenMany(updateImportedUser(user)))
        .map(mono->mono);
  }

  /**
   * this is the entry point when application starts
   * we get all users from database and sends them to the importer
   * @param args application arguments that we don't use
   * @throws Exception the problem
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      log.info("application started - started import");
      importFluxOfUsers(usersRepository.findAll());
    }catch (Exception ex){
      log.error(ex);
      log.info("set the liveness broken - self healing");
      throw ex;
    }
  }

  /**
   * we schedule a import every day at 1 o'clock
   * we get all users from database and sends them to the importer
   * @throws Exception the problem
   */
  @Scheduled(cron = "${importer.scheduler.cron}")
  public void run() throws Exception {
    try {
      log.info("started scheduled import");
      importFluxOfUsers(usersRepository.findAll());
    }catch (Exception ex){
      log.error(ex);
      log.info("set the liveness broken - self healing");
      throw ex;
    }
  }
}
