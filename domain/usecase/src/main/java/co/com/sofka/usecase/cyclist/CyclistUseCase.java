package co.com.sofka.usecase.cyclist;

import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CyclistUseCase {

    private final CyclistRepository cyclistRepository;

    public Flux<Cyclist> findByCountry(String country){
        return Mono.just(country.toUpperCase())
                .flatMapMany(cyclistRepository::findCyclistByNationality);
    }
}
