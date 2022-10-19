package co.com.sofka.model.cyclist.gateways;

import co.com.sofka.model.cyclist.Cyclist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CyclistRepository {

    Mono<Cyclist> create(Cyclist cyclist);

    Flux<Cyclist> findCyclistByNationality(String country);
}
