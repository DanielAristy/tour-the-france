package co.com.sofka.mongo.cyclist;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

public interface CyclistDBRepository extends ReactiveMongoRepository<CyclistData, String>, ReactiveQueryByExampleExecutor<CyclistData> {
    Flux<CyclistData> findByCountry(String country);
}