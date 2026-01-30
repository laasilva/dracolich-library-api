package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.ClassEntity;
import dm.dracolich.library.web.entity.RaceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClassRepository extends ReactiveMongoRepository<ClassEntity, String>, ReactiveQueryByExampleExecutor<ClassEntity> {
    Mono<ClassEntity> findByNameIgnoreCase(String name);
    Flux<ClassEntity> findAllByClassNameIgnoreCase(String className);
}
