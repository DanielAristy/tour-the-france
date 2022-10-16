package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CountryUseCase {

    public Mono<Country> executePost(Country country){
        return Mono.just(country);
    }
}
