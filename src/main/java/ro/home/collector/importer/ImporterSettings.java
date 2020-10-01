package ro.home.collector.importer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "importer")
public class ImporterSettings {
  private String accountsUri = "/accounts";
  private String transactionsUri = "/transactions";
  private String loginUri = "/login";
  private String baseUrl = "http://localhost:8080";
}
