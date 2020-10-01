package ro.home.collector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Data
@UserDefinedType("exchangerate")
public class ExchangeRate{
  private String currencyFrom;
  private String currencyTo;
  private Double rate;
}
