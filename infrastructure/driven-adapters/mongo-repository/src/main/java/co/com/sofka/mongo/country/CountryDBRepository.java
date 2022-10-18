package co.com.sofka.mongo.country;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface CountryDBRepository extends ReactiveMongoRepository<CountryData, String>, ReactiveQueryByExampleExecutor<CountryData> {

    Mono<CountryData> findByName(String name);
}
