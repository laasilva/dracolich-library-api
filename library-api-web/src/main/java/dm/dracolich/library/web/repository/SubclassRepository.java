package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.SubclassEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubclassRepository extends ReactiveMongoRepository<SubclassEntity, String>, ReactiveQueryByExampleExecutor<SubclassEntity> {
    Mono<SubclassEntity> findByNameIgnoreCase(String name);
    Flux<SubclassEntity> findAllByClassNameIgnoreCase(String className);
    Flux<SubclassEntity> findAllByNameContainingIgnoreCase(String name);
    Flux<SubclassEntity> findAllByNameContainingIgnoreCase(String name, Example<SubclassEntity> example);
}
