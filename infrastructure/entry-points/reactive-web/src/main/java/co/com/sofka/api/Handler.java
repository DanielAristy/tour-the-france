package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.api.dto.CyclistDTO;
import co.com.sofka.api.dto.TeamDTO;
import co.com.sofka.model.enums.Response;
import co.com.sofka.model.enums.Variable;
import co.com.sofka.usecase.country.CountryUseCase;
import co.com.sofka.usecase.cyclisteam.CyclistTeamUseCase;
import co.com.sofka.usecase.team.TeamUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Handler implements HandlerOperation {
    private final CountryUseCase countryUseCase;
    private final TeamUseCase teamUseCase;

    private final CyclistTeamUseCase cyclistTeamUseCase;
    private final ObjectMapper mapper;

    public Mono<ServerResponse> listenPostCountryUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CountryDTO.class))
                .map(countryDTO -> countryToEntity(countryDTO, mapper))
                .flatMap(countryUseCase::executePost)
                .map(country -> countryToDTO(country, mapper))
                .flatMap(countryDTO -> ServerResponse.ok().bodyValue(countryDTO));
    }

    public Mono<ServerResponse> listenDeleteCountryUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.NAME.getValue()))
                .flatMap(nameCountry -> !nameCountry.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+$")
                        ? ServerResponse.badRequest().bodyValue(Response.PARAMETER_WITH_DIGITS.getValue())
                        : countryUseCase.executeDelete(nameCountry)
                        .flatMap(this::validateResponse));
    }

    public Mono<ServerResponse> listenFindCountryUseCase(ServerRequest serverRequest) {

        return countryUseCase.executeFind()
                .map(country -> countryToDTO(country, mapper))
                .collectList()
                .flatMap(countries -> ServerResponse.ok().bodyValue(countries));
    }

    public Mono<ServerResponse> listenPostTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(TeamDTO.class))
                .flatMap(teamDTO -> Objects.isNull(teamDTO.getCode()) || !teamDTO.getCode().matches("^[a-zA-Z\\d\\\\s]{1,3}+$") ?
                        ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : Mono.just(teamToEntity(teamDTO, mapper))
                        .flatMap(teamUseCase::executePost)
                        .map(country -> teamToDTO(country, mapper))
                        .flatMap(teamDto -> ServerResponse.ok().bodyValue(teamDto))
                );
    }

    public Mono<ServerResponse> listenDeleteTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.CODE.getValue()))
                .flatMap(codeTeam -> !codeTeam.matches("^[a-zA-Z\\d\\\\s]{1,3}+$") ?
                        ServerResponse.badRequest().bodyValue(Response.PARAMETER_WITH_DIGITS.getValue())
                        : teamUseCase.executeDelete(codeTeam)
                        .flatMap(this::validateResponse));
    }

    public Mono<ServerResponse> listenFindTeamUseCase(ServerRequest serverRequest) {

        return teamUseCase.executeFind()
                .map(team -> teamToDTO(team, mapper))
                .collectList()
                .flatMap(teams -> ServerResponse.ok().bodyValue(teams));
    }

    public Mono<ServerResponse> listenFindByCountryTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.NAME.getValue()))
                .flatMapMany(teamUseCase::executeFindByCountry)
                .map(team -> teamToDTO(team, mapper))
                .collectList()
                .flatMap(teams -> ServerResponse.ok().bodyValue(teams));
    }

    public Mono<ServerResponse> listenPostCyclistTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CyclistDTO.class))
                .flatMap(cyclistDTO -> Objects.isNull(cyclistDTO.getTeamCode()) || !(cyclistDTO.getTeamCode().matches("^[a-zA-Z\\d\\\\s]{1,3}+$")) ?
                        ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : Mono.just(cyclistToEntity(cyclistDTO, mapper))
                        .flatMap(cyclistTeamUseCase::executePost)
                        .flatMap(this::validateResponse)
                );
    }

    public Mono<ServerResponse> listenGetCyclistByTeamUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .map(request -> request.pathVariable("teamCode"))
                .flatMap(teamCode -> !teamCode.matches("^[a-zA-Z\\d\\\\s]{1,3}+$")
                        ? ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : teamUseCase.findCyclistByTeamCode(teamCode)
                        .flatMap(cyclists -> ServerResponse.ok().bodyValue(cyclists)));
    }

    private Mono<ServerResponse> validateResponse(String response) {
        return Response.NOT_FOUND_TEAM.getValue().equals(response) ||
                Response.NOT_FOUND_COUNTRY.getValue().equals(response) ||
                Response.CYCLIST_NOT_ADDED.getValue().equals(response) ||
                Response.RECORD_NOT_FOUND.getValue().equals(response)
                ? ServerResponse.badRequest().bodyValue(response)
                : ServerResponse.ok().bodyValue(response);
    }
}
