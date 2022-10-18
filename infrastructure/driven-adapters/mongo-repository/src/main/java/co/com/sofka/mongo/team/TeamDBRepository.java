package co.com.sofka.mongo.team;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface TeamDBRepository extends ReactiveMongoRepository<TeamData, String>, ReactiveQueryByExampleExecutor<TeamData> {

}
