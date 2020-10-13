package ro.home.collector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by LaurentiuM on 29/09/2020.
 */
@Data
@Table("transactions")
public class TransactionsDto extends ComposedPrimaryKey implements Serializable {
  private String accountId;
  private ExchangeRate exchangeRate;
  private OriginalAmount originalAmount;
  private Creditor creditor;
  private Debtor debtor;
  private String status;
  private String currency;
  private Double amount;
  @JsonProperty("update")
  private String updatedate;
  private String description;
}
