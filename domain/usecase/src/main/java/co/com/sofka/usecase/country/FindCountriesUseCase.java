package co.com.sofka.usecase.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindCountriesUseCase {
    private final CountryRepository countryRepository;

    public Flux<Country> findAll() {
        return countryRepository.findAllCountries();
    }
}
