package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.enums.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CountryUseCase {
    private final CountryRepository countryRepository;

    public Mono<Country> executePost(Country countryData) {
        return Mono.just(countryData)
                .map(country -> country.toBuilder().name(country.getName().toUpperCase()).build())
                .flatMap(countryRepository::create);
    }

    public Mono<String> executeDelete(String name) {
        return Mono.just(name)
                .map(String::toUpperCase)
                .flatMap(nameCountry -> countryRepository.findByName(nameCountry)
                        .flatMap(country -> countryRepository.delete(country)
                                .then(Mono.just(Response.RECORD_DELETED.getValue()))))
                .switchIfEmpty(Mono.just(Response.RECORD_NOT_FOUND.getValue()));
    }

    public Flux<Country> executeFind() {
        return countryRepository.findAllCountries();
    }
}
