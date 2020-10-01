package ro.home.collector.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Data
@UserDefinedType("debtor")
public class Debtor{
  private String maskedPan;
  private String name;
}
