package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CountryUseCase {
    private final CountryRepository countryRepository;

    public Mono<Country> executePost(Country countryData) {
        return Mono.just(countryData)
                .map(country -> country.toBuilder().name(country.getName().toUpperCase()).build())
                .flatMap(country -> countryRepository.create(country));
    }

    public Mono<Void> executeDelete(String name) {
        return Mono.just(name)
                .map(String::toUpperCase)
                .flatMap(nameCountry -> countryRepository.delete(nameCountry));
    }
}
