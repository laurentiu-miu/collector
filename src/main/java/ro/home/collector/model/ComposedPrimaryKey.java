package ro.home.collector.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * Created by LaurentiuM on 29/09/2020.
 */
@Data
@NoArgsConstructor
public class ComposedPrimaryKey implements Serializable {
  @PrimaryKeyColumn(name = "username",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
  private String username;
  @PrimaryKeyColumn(name = "importDate",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
  private LocalDate importDate;
  @PrimaryKeyColumn(name = "id",ordinal = 2,type = PrimaryKeyType.CLUSTERED)
  private String id;
}
