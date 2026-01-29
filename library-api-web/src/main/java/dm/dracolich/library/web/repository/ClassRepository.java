package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.ClassEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRepository extends ReactiveMongoRepository<ClassEntity, String> {
    Mono<ClassEntity> findByName(String name);
}
