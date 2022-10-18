package co.com.sofka.model.team.gateways;

import co.com.sofka.model.team.Team;
import reactor.core.publisher.Mono;

public interface TeamRepository {

    Mono<Team> create(Team team);
}
