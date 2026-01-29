package dm.dracolich.library.web.repository;

import dm.dracolich.library.dto.enums.EquipmentCategoryEnum;
import dm.dracolich.library.web.entity.EquipmentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EquipmentRepository extends ReactiveMongoRepository<EquipmentEntity, String> {
    Mono<EquipmentEntity> findByName(String name);
    Flux<EquipmentEntity> findByEquipmentCategory(EquipmentCategoryEnum category);
}
