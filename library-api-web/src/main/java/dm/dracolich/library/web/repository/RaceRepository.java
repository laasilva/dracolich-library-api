package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.RaceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RaceRepository extends ReactiveMongoRepository<RaceEntity, String> {
    Mono<RaceEntity> findByNameIgnoreCase(String name);
}