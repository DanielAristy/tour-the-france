package co.com.sofka.model.country.gateways;

import co.com.sofka.model.country.Country;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryRepository {
    Mono<Country> create(Country country);

    Mono<Country> findByName(String name);

    Mono<Void> delete(Country country);

    Flux<Country> findAllCountries();
}
