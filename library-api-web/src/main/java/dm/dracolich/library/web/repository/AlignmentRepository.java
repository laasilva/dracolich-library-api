package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.AlignmentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AlignmentRepository extends ReactiveMongoRepository<AlignmentEntity, String> {
    Mono<AlignmentEntity> findByName(String name);
}
