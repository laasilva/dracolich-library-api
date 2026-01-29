package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.BackgroundEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BackgroundRepository extends ReactiveMongoRepository<BackgroundEntity, String> {
    Mono<BackgroundEntity> findByName(String name);
}
