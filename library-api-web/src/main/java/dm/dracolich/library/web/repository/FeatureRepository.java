package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.FeatureEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FeatureRepository extends ReactiveMongoRepository<FeatureEntity, String> {
    Mono<FeatureEntity> findByName(String name);
}
