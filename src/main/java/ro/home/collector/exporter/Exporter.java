package ro.home.collector.exporter;

import java.time.LocalDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ro.home.collector.model.AccountsDto;
import ro.home.collector.model.TransactionsDto;
import ro.home.collector.repository.AccountsRepository;
import ro.home.collector.repository.TransactionsRepository;

/**
 * Created by LaurentiuM on 29/09/2020.
 * These is were we expose our imported data.
 */
@RestController
@Log4j2
public class Exporter {
  private AccountsRepository accountsRepository;
  private TransactionsRepository transactionsRepository;

  public Exporter(AccountsRepository accountsRepository,
      TransactionsRepository transactionsRepository) {
    this.accountsRepository = accountsRepository;
    this.transactionsRepository = transactionsRepository;
  }

  @GetMapping("/accounts/{username}")
  public Flux<AccountsDto> getAccountsForUser(@PathVariable String username){
    log.info("accounts endpoint called for username:{}",username);
    return accountsRepository.findByUsernameAndImportDate(username,LocalDate.now());
  }
  @GetMapping("/transactions/{username}")
  public Flux<TransactionsDto> getTransactionsForUser(@PathVariable String username){
    log.info("transactions endpoint called for username:{}",username);
    return transactionsRepository.findByUsernameAndImportDate(username,LocalDate.now());
  }
}
