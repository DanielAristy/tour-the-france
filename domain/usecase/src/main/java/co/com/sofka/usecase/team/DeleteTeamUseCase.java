package co.com.sofka.usecase.team;

import co.com.sofka.model.enums.Response;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteTeamUseCase {

    private final TeamRepository teamRepository;

    public Mono<String> deleteTeam(String code) {
        return Mono.just(code)
                .map(String::toUpperCase)
                .flatMap(teamCode -> teamRepository.findByCode(teamCode)
                        .flatMap(team -> teamRepository.delete(team)
                                .then(Mono.just(Response.RECORD_DELETED.getValue()))))
                .switchIfEmpty(Mono.just(Response.RECORD_NOT_FOUND.getValue()));
    }
}
