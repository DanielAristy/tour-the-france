package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateCountryUseCase {
    private final CountryRepository countryRepository;

    public Mono<Country> createCountry(Country countryData) {
        return Mono.just(countryData)
                .map(country -> country.toBuilder()
                        .name(country.getName().toUpperCase())
                        .code(country.getCode().toUpperCase())
                        .build())
                .flatMap(countryRepository::create);
    }
}
