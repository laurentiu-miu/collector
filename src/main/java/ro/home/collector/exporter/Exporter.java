package ro.home.collector.exporter;

import java.time.LocalDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.home.collector.Utility;
import ro.home.collector.importer.Importer;
import ro.home.collector.model.AccountsDto;
import ro.home.collector.model.TransactionsDto;
import ro.home.collector.model.UsersDto;
import ro.home.collector.repository.AccountsRepository;
import ro.home.collector.repository.TransactionsRepository;
import ro.home.collector.repository.UsersRepository;

/**
 * Created by LaurentiuM on 29/09/2020.
 * These is were we expose our imported data.
 */
@RestController
@Log4j2
public class Exporter {
  private UsersRepository usersRepository;
  private AccountsRepository accountsRepository;
  private TransactionsRepository transactionsRepository;

  public Exporter(UsersRepository usersRepository,
      AccountsRepository accountsRepository,
      TransactionsRepository transactionsRepository) {
    this.usersRepository = usersRepository;
    this.accountsRepository = accountsRepository;
    this.transactionsRepository = transactionsRepository;
  }

  @GetMapping("/accounts/{username}")
  public Flux<AccountsDto> getAccountsForUser(@PathVariable String username){
    log.info("accounts endpoint call for username:{}",username);
    return usersRepository.findByUsername(username)
        .filter(Utility::wasNotUpdatedToday)
        .switchIfEmpty(Importer.importSingleUser(UsersDto.builder().username(username).build()))
        .thenMany(accountsRepository.findByUsernameAndImportDate(username,LocalDate.now()));
  }
  @GetMapping("/transactions/{username}")
  public Flux<TransactionsDto> getTransactionsForUser(@PathVariable String username){
    log.info("transactions endpoint call for username:{}",username);
    return usersRepository.findByUsername(username)
        .filter(Utility::wasNotUpdatedToday)
        .switchIfEmpty(Importer.importSingleUser(UsersDto.builder().username(username).build()))
        .thenMany(transactionsRepository.findByUsernameAndImportDate(username,LocalDate.now()))
        .onErrorResume(Exception.class, e -> {
          TransactionsDto tx = new TransactionsDto();
          tx.setException(e.getMessage());
          return Flux.just(tx);
          });
  }
}
