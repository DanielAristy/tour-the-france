package co.com.sofka.usecase.cyclisteam;

import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import co.com.sofka.model.enums.Response;
import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CyclistTeamUseCase {

    private final CyclistRepository cyclistRepository;
    private final TeamRepository teamRepository;
    private final CountryRepository countryRepository;

    public Mono<String> executePost(Cyclist cyclist) {
        return Mono.just(cyclist)
                .map(cyclistData -> cyclistData.toBuilder()
                        .teamCode(cyclist.getTeamCode().toUpperCase())
                        .country(cyclistData.getCountry().toUpperCase())
                        .build())
                .flatMap(cyclistData -> teamRepository.findByCode(cyclistData.getTeamCode())
                        .flatMap(team -> countryRepository.findByName(cyclistData.getCountry())
                                .flatMap(country -> cyclistRepository.create(cyclistData)
                                        .map(cyclistDetails -> {
                                            Team teamData = team.toBuilder().build();
                                            teamData.getCyclists().add(cyclistDetails);
                                            return teamData;
                                        })
                                        .flatMap(teamDetails -> teamRepository.create(team)
                                                .then(Mono.just(Response.UPDATE_TEAM_SUCCESSFULLY.getValue())))
                                        .switchIfEmpty(Mono.just(Response.CYCLIST_NOT_ADDED.getValue().concat(team.getCode())))
                                ).switchIfEmpty(Mono.just(Response.NOT_FOUND_COUNTRY.getValue()))
                        ).switchIfEmpty(Mono.just(Response.NOT_FOUND_TEAM.getValue()))
                );
    }

    public Mono<List<Cyclist>> findCyclistByTeamCode(String teamCode) {
        return teamRepository.findByCode(teamCode.toUpperCase())
                .flatMap(team -> Objects.isNull(team.getCyclists())
                        ? Mono.just(new ArrayList<>())
                        : Mono.just(team.getCyclists())
                );

    }
}
