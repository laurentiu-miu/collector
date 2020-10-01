package ro.home.collector.repository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ro.home.collector.model.ComposedPrimaryKey;
import ro.home.collector.model.UsersDto;

/**
 * Created by LaurentiuM on 30/09/2020.
 */
@Repository
public interface UsersRepository extends ReactiveCassandraRepository<UsersDto, ComposedPrimaryKey> {
  @Query("select * from accounts where username = ?0")
  Mono<UsersDto> findByUsername(@Param("username") String username);
}
