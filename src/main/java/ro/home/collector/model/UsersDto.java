package ro.home.collector.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by LaurentiuM on 29/09/2020.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table("users")
public class UsersDto {
  @PrimaryKey
  private String username;
  private LocalDate importDate;
  @Transient
  private String jwt;
}
