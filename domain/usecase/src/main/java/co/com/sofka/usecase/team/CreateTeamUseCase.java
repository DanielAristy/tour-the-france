package co.com.sofka.usecase.team;

import co.com.sofka.model.country.Country;
import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTeamUseCase {

    private final TeamRepository teamRepository;

    public Mono<Team> createTeam(Team team) {
        return Mono.just(team)
                .map(teamData -> teamData.toBuilder().code(teamData.getCode().toUpperCase())
                        .country(Country.builder()
                                .name(teamData.getCountry().getName().toUpperCase())
                                .code(teamData.getCountry().getCode().toUpperCase())
                                .build())
                        .build())
                .flatMap(teamRepository::create);
    }
}
