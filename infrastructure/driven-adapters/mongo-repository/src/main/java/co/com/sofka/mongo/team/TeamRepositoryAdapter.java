package co.com.sofka.mongo.team;

import co.com.sofka.model.team.Team;
import co.com.sofka.model.team.gateways.TeamRepository;
import co.com.sofka.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TeamRepositoryAdapter extends AdapterOperations<Team, TeamData, String, TeamDBRepository>
        implements TeamRepository {

    public TeamRepositoryAdapter(TeamDBRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, Team.class));
    }

    @Override
    public Mono<Team> create(Team team) {
        return repository.save(mapper.map(team, TeamData.class))
                .map(this::toEntity);
    }

    @Override
    public Mono<Team> findByCode(String code) {
        return repository.findByCode(code).map(this::toEntity);
    }

    @Override
    public Mono<Void> delete(Team team) {
        return repository.delete(mapper.map(team, TeamData.class));
    }

    @Override
    public Flux<Team> findAllTeams() {
        return repository.findAll().map(this::toEntity);
    }

    @Override
    public Flux<Team> findByCountryName(String name) {
        return doQueryMany(repository.findByCountryName(name));
    }
}
