package co.com.sofka.usecase.cyclist;

import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import co.com.sofka.model.enums.Response;
import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateCyclistUseCase {

    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    private final CountryRepository countryRepository;

    public Mono<String> createCyclist(Cyclist cyclist) {
        return Mono.just(cyclist)
                .map(cyclistData -> cyclistData.toBuilder()
                        .teamCode(cyclist.getTeamCode().toUpperCase())
                        .country(cyclistData.getCountry().toUpperCase())
                        .build())
                .flatMap(cyclistData -> teamRepository.findByCode(cyclistData.getTeamCode())
                        .flatMap(team -> countryRepository.findByName(cyclistData.getCountry())
                                .flatMap(country -> cyclistRepository.create(cyclistData)
                                        .flatMap(cyclistDetails -> team.getCyclists().isEmpty() || Objects.isNull(team.getCyclists())
                                                ? Mono.just(team.toBuilder().cyclists(List.of(cyclistDetails)).build())
                                                .flatMap(this::updateTeam)
                                                : Mono.just(team.toBuilder().build())
                                                .map(teamDetails -> {
                                                    Team teamData = teamDetails.toBuilder().build();
                                                    teamData.getCyclists().add(cyclistDetails);
                                                    return teamData;
                                                })
                                                .flatMap(this::updateTeam)
                                        ).switchIfEmpty(Mono.just(Response.CYCLIST_NOT_ADDED.getValue().concat(team.getCode())))
                                ).switchIfEmpty(Mono.just(Response.NOT_FOUND_COUNTRY.getValue()))
                        ).switchIfEmpty(Mono.just(Response.NOT_FOUND_TEAM.getValue()))
                );
    }


    Mono<String> updateTeam(Team team) {
        return teamRepository.create(team)
                .then(Mono.just(Response.UPDATE_TEAM_SUCCESSFULLY.getValue()));
    }
}
