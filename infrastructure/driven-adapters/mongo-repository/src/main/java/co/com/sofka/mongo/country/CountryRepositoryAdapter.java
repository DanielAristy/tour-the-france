package co.com.sofka.mongo.country;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CountryRepositoryAdapter extends AdapterOperations<Country, CountryData, String, CountryDBRepository>
        implements CountryRepository {

    public CountryRepositoryAdapter(CountryDBRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, Country.class));
    }

    @Override
    public Mono<Country> create(Country country) {
        return repository.save(mapper.map(country, CountryData.class))
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> delete(String name) {
        return repository.findByName(name)
                .flatMap(countryData -> repository.delete(countryData));
    }

    @Override
    public Flux<Country> findAllCountries() {
        return repository.findAll()
                .map(this::toEntity);
    }
}
