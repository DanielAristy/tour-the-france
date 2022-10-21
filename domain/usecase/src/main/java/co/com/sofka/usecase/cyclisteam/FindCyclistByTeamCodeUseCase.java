package co.com.sofka.usecase.cyclisteam;

import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class FindCyclistByTeamCodeUseCase {
    private final TeamRepository teamRepository;

    public Mono<List<Cyclist>> findByTeamCode(String teamCode) {
        return teamRepository.findByCode(teamCode.toUpperCase())
                .flatMap(team -> Objects.isNull(team.getCyclists())
                        ? Mono.just(new ArrayList<>())
                        : Mono.just(team.getCyclists())
                );

    }
}
