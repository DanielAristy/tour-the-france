package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.api.dto.CyclistDTO;
import co.com.sofka.api.dto.TeamDTO;
import co.com.sofka.model.enums.Response;
import co.com.sofka.model.enums.Variable;
import co.com.sofka.usecase.country.CreateCountryUseCase;
import co.com.sofka.usecase.country.DeleteCountryUseCase;
import co.com.sofka.usecase.country.FindCountriesUseCase;
import co.com.sofka.usecase.cyclist.CreateCyclistUseCase;
import co.com.sofka.usecase.cyclist.FindCyclistByCountryUseCase;
import co.com.sofka.usecase.cyclisteam.FindCyclistByTeamCodeUseCase;
import co.com.sofka.usecase.team.CreateTeamUseCase;
import co.com.sofka.usecase.team.DeleteTeamUseCase;
import co.com.sofka.usecase.team.FindTeamByCountryUseCase;
import co.com.sofka.usecase.team.FindTeamsUseCase;
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

    private final CreateTeamUseCase createTeamUseCase;
    private final DeleteTeamUseCase deleteTeamUseCase;
    private final FindTeamsUseCase findTeamsUseCase;
    private final FindTeamByCountryUseCase findTeamByCountryUseCase;
    private final FindCyclistByTeamCodeUseCase findCyclistByTeamCodeUseCase;
    private final CreateCountryUseCase createCountryUseCase;
    private final DeleteCountryUseCase deleteCountryUseCase;
    private final FindCountriesUseCase findCountriesUseCase;
    private final FindCyclistByCountryUseCase findCyclistByCountryUseCase;

    private final CreateCyclistUseCase createCyclistUseCase;

    private final ObjectMapper mapper;

    public Mono<ServerResponse> listenPostTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(TeamDTO.class))
                .flatMap(teamDTO -> Objects.isNull(teamDTO.getCode()) || !teamDTO.getCode().matches("^[a-zA-Z\\d\\\\s]{1,3}+$") ?
                        ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : Mono.just(teamToEntity(teamDTO, mapper))
                        .flatMap(createTeamUseCase::createTeam)
                        .map(country -> teamToDTO(country, mapper))
                        .flatMap(teamDto -> ServerResponse.ok().bodyValue(teamDto))
                );
    }

    public Mono<ServerResponse> listenDeleteTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.CODE.getValue()))
                .flatMap(codeTeam -> !codeTeam.matches("^[a-zA-Z\\d\\\\s]{1,3}+$") ?
                        ServerResponse.badRequest().bodyValue(Response.PARAMETER_WITH_DIGITS.getValue())
                        : deleteTeamUseCase.deleteTeam(codeTeam)
                        .flatMap(this::validateResponse));
    }

    public Mono<ServerResponse> listenFindTeamUseCase(ServerRequest serverRequest) {

        return findTeamsUseCase.findTeams()
                .map(team -> teamToDTO(team, mapper))
                .collectList()
                .flatMap(teams -> ServerResponse.ok().bodyValue(teams));
    }

    public Mono<ServerResponse> listenGetCyclistByTeamUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .map(request -> request.pathVariable("teamCode"))
                .flatMap(teamCode -> !teamCode.matches("^[a-zA-Z\\d\\\\s]{1,3}+$")
                        ? ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : findCyclistByTeamCodeUseCase.findByTeamCode(teamCode)
                        .flatMap(cyclists -> ServerResponse.ok().bodyValue(cyclists))
                );
    }

    public Mono<ServerResponse> listenFindByCountryTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.NAME.getValue()))
                .flatMapMany(findTeamByCountryUseCase::findByCountry)
                .map(team -> teamToDTO(team, mapper))
                .collectList()
                .flatMap(teams -> ServerResponse.ok().bodyValue(teams));
    }

    public Mono<ServerResponse> listenPostCountryUseCase(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CountryDTO.class))
                .map(countryDTO -> countryToEntity(countryDTO, mapper))
                .flatMap(createCountryUseCase::createCountry)
                .map(country -> countryToDTO(country, mapper))
                .flatMap(countryDTO -> ServerResponse.ok().bodyValue(countryDTO));
    }

    public Mono<ServerResponse> listenDeleteCountryUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable(Variable.NAME.getValue()))
                .flatMap(nameCountry -> !nameCountry.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+$")
                        ? ServerResponse.badRequest().bodyValue(Response.PARAMETER_WITH_DIGITS.getValue())
                        : deleteCountryUseCase.delete(nameCountry)
                        .flatMap(this::validateResponse));
    }

    public Mono<ServerResponse> listenFindCountryUseCase(ServerRequest serverRequest) {

        return findCountriesUseCase.findAll()
                .map(country -> countryToDTO(country, mapper))
                .collectList()
                .flatMap(countries -> ServerResponse.ok().bodyValue(countries));
    }

    public Mono<ServerResponse> listenPostCyclistTeamUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(request -> request.bodyToMono(CyclistDTO.class))
                .flatMap(cyclistDTO -> Objects.isNull(cyclistDTO.getTeamCode()) || !(cyclistDTO.getTeamCode().matches("^[a-zA-Z\\d\\\\s]{1,3}+$")) ?
                        ServerResponse.badRequest().bodyValue(Response.ALPHANUMERIC.getValue())
                        : Mono.just(cyclistToEntity(cyclistDTO, mapper))
                        .flatMap(createCyclistUseCase::createCyclist)
                        .flatMap(this::validateResponse)
                );
    }

    public Mono<ServerResponse> listenFindCyclistUseCase(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .map(request -> request.pathVariable("nationality"))
                .flatMapMany(findCyclistByCountryUseCase::findByCountry)
                .collectList()
                .flatMap(cyclists -> ServerResponse.ok().bodyValue(cyclists));
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
