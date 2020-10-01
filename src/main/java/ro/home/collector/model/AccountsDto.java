package ro.home.collector.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by LaurentiuM on 29/09/2020.
 */
@Data
@Table("accounts")
public class AccountsDto extends ComposedPrimaryKey implements Serializable {
  @JsonProperty("update")
  private String updateDate;
  private String name;
  private String product;
  private String status;
  private String type;
  private Double balance;
}
