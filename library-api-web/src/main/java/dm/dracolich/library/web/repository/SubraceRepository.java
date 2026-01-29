package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.SubraceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubraceRepository extends ReactiveMongoRepository<SubraceEntity, String> {
    Mono<SubraceEntity> findByName(String name);
    Flux<SubraceEntity> findByRaceId(String raceId);
}
