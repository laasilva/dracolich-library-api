package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.SubraceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubraceRepository extends ReactiveMongoRepository<SubraceEntity, String>, ReactiveQueryByExampleExecutor<SubraceEntity> {
    Mono<SubraceEntity> findByNameIgnoreCase(String name);
    Flux<SubraceEntity> findAllByNameContainingIgnoreCase(String raceName);
    Flux<SubraceEntity> findAllByRaceNameIgnoreCase(String raceName);
}
