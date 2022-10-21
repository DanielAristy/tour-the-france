package co.com.sofka.usecase.team;

import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindTeamsUseCase {

    private final TeamRepository teamRepository;

    public Flux<Team> findTeams() {
        return teamRepository.findAllTeams();
    }
}
