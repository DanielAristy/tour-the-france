package co.com.sofka.usecase.cyclisteam;

import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.cyclist.Cyclist;
import co.com.sofka.model.cyclist.gateways.CyclistRepository;
import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
                                        ).switchIfEmpty(Mono.just("No se pudo agregar el ciclista al equipo ".concat(team.getCode())))
                                ).switchIfEmpty(Mono.just("No existe pais en base de datos"))
                        ).switchIfEmpty(Mono.defer(() -> Mono.just("No se ha encontrado equipo de referencia")))
                );
    }

    Mono<String> updateTeam(Team team) {
        return teamRepository.create(team)
                .then(Mono.just("Se ha actualizado el equipo correctamente"));
    }
}
