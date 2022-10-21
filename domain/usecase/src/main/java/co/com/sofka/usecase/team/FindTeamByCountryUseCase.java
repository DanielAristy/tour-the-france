package co.com.sofka.usecase.team;

import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindTeamByCountryUseCase {

    private final TeamRepository teamRepository;

    public Flux<Team> findByCountry(String name) {
        return teamRepository.findByCountryName(name.toUpperCase());
    }
}
