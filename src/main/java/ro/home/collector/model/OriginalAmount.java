package ro.home.collector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Data
@UserDefinedType("originalamount")
public class OriginalAmount{
  private Double amount;
  private String currency;
}
