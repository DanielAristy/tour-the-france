package co.com.sofka.usecase.cyclist;

import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CyclistUseCase {

    private final CyclistRepository cyclistRepository;

    public Mono<Cyclist> executePost(Cyclist cyclist) {
        return Mono.just(cyclist)
                .map(cyclistData -> cyclistData.toBuilder()
                        .code(cyclistData.getCode().toUpperCase())
                        .country(cyclistData.getCountry().toBuilder()
                                .code(cyclistData.getCountry().getCode().toUpperCase())
                                .name(cyclistData.getCountry().getName().toUpperCase())
                                .build())
                        .build())
                .flatMap(cyclistData -> cyclistRepository.create(cyclistData));
    }
}
