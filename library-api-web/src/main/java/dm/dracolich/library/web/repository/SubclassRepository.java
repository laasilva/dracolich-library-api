package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.SubclassEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubclassRepository extends ReactiveMongoRepository<SubclassEntity, String> {
    Mono<SubclassEntity> findByName(String name);
    Flux<SubclassEntity> findAllByClassId(String classId);
}
