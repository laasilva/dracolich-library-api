package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.AttributeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AttributeRepository extends ReactiveMongoRepository<AttributeEntity, String> {
    Mono<AttributeEntity> findByName(String name);
}