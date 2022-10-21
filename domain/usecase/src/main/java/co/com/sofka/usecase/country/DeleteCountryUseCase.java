package co.com.sofka.usecase.country;

import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.enums.Response;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteCountryUseCase {

    private final CountryRepository countryRepository;

    public Mono<String> delete(String name) {
        return Mono.just(name)
                .map(String::toUpperCase)
                .flatMap(nameCountry -> countryRepository.findByName(nameCountry)
                        .flatMap(country -> countryRepository.delete(country)
                                .then(Mono.just(Response.RECORD_DELETED.getValue()))))
                .switchIfEmpty(Mono.just(Response.RECORD_NOT_FOUND.getValue()));
    }
}
