package dm.dracolich.library.web.repository;

import dm.dracolich.library.web.entity.SpellEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpellRepository extends ReactiveMongoRepository<SpellEntity, String> {
    Mono<SpellEntity> findByName(String name);
    Flux<SpellEntity> findByMinSlotLevel(Integer level);
}
