package co.com.sofka.usecase.team;

import co.com.sofka.model.country.gateways.CountryRepository;
import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TeamUseCase {

    private final TeamRepository teamRepository;
    private final CountryRepository countryRepository;

    public Mono<Team> executePost(Team team) {
        return countryRepository.findAllCountries()
                .filter(country -> country.getCode().equals(team.getCountry().getCode()))
                .next()
                .flatMap(country -> teamRepository.create(team));
    }
}
