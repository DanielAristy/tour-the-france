package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.usecase.country.CountryUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler implements HandlerOperation {
    private final CountryUseCase countryUseCase;
    private final ObjectMapper mapper;
    public Mono<ServerResponse> listenPOSTCountryUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CountryDTO.class))
                .map(countryDTO -> mapperCountry(countryDTO, mapper))
                .flatMap(country -> countryUseCase.executePost(country))
                .flatMap(country -> ServerResponse.ok().bodyValue(country));
    }
}
