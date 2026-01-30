package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.RaceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RaceRepository extends ReactiveMongoRepository<RaceEntity, String>, ReactiveQueryByExampleExecutor<RaceEntity> {
    Mono<RaceEntity> findByNameIgnoreCase(String name);
}