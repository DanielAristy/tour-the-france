package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CountryUseCase {
    private final CountryRepository countryRepository;
    public Mono<Country> executePost(Country countryData){
        return Mono.just(countryData)
                .flatMap(country -> countryRepository.create(country));
    }
}
