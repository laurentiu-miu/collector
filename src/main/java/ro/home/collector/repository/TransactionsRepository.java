package ro.home.collector.repository;

import java.time.LocalDate;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ro.home.collector.model.TransactionsDto;
import ro.home.collector.model.ComposedPrimaryKey;

/**
 * Created by LaurentiuM on 29/09/2020.
 */
@Repository
public interface TransactionsRepository extends ReactiveCassandraRepository<TransactionsDto, ComposedPrimaryKey> {
  @Query("select * from transactions where username = ?0 and importdate = ?1")
  Flux<TransactionsDto> findByUsernameAndImportDate(@Param("username") String username, @Param("importDate") LocalDate importDate);
}
