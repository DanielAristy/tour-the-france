package co.com.sofka.model.country.gateways;

import co.com.sofka.model.country.Country;
import reactor.core.publisher.Mono;

public interface CountryRepository {
    Mono<Country> create(Country country);
}
