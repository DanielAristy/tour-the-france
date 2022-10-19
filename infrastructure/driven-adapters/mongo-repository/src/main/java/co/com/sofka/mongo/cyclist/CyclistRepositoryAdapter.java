package co.com.sofka.mongo.cyclist;

import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CyclistRepositoryAdapter extends AdapterOperations<Cyclist, CyclistData, String, CyclistDBRepository>
        implements CyclistRepository {

    public CyclistRepositoryAdapter(CyclistDBRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, Cyclist.class));
    }


    @Override
    public Mono<Cyclist> create(Cyclist cyclist) {
        return repository.save(mapper.map(cyclist, CyclistData.class))
                .map(this::toEntity);
    }

    @Override
    public Flux<Cyclist> findCyclistByNationality(String country) {
        return repository.findByCountry(country).map(this::toEntity);
    }
}
