package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.api.dto.TeamDTO;
import co.com.sofka.model.country.Country;
import co.com.sofka.usecase.country.CountryUseCase;
import co.com.sofka.usecase.team.TeamUseCase;
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
    private final TeamUseCase teamUseCase;
    private final ObjectMapper mapper;

    public Mono<ServerResponse> listenPOSTCountryUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CountryDTO.class))
                .map(countryDTO -> countryToEntity(countryDTO, mapper))
                .flatMap(countryUseCase::executePost)
                .map(country -> countryToDTO(country, mapper))
                .flatMap(countryDTO -> ServerResponse.ok().bodyValue(countryDTO));
    }

    public Mono<ServerResponse> listenDELETECountryUseCase(ServerRequest serverRequest) {

        String name = serverRequest.pathVariable("name");
        return countryUseCase.executeDelete(name)
                .flatMap(country -> ServerResponse.ok().bodyValue(country));
    }

    public Mono<ServerResponse> listenFINDCountryUseCase(ServerRequest serverRequest) {

        return ServerResponse.ok().body(countryUseCase.executeFind(), Country.class);
    }

    public Mono<ServerResponse> listenPOSTTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(TeamDTO.class))
                .map(teamDTO -> teamToEntity(teamDTO, mapper))
                .flatMap(teamUseCase::executePost)
                .map(country -> teamToDTO(country, mapper))
                .flatMap(teamDTO -> ServerResponse.ok().bodyValue(teamDTO));
    }
}
