package dm.dracolich.library.web.config;

import dm.dracolich.library.web.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MongoIndexConfig {

    private final ReactiveMongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        log.info("Creating MongoDB indexes...");

        // Unique index on attribute name
        createIndexSafe(AttributeEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "attributes.name");

        // Unique index on race name
        createIndexSafe(RaceEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "races.name");

        // Unique index on class name
        createIndexSafe(ClassEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "classes.name");

        // Unique index on subclass name
        createIndexSafe(SubclassEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "subclasses.name");

        // Index on subclass className for lookups
        createIndexSafe(SubclassEntity.class, new Index().on("className", Sort.Direction.ASC), "subclasses.classId");

        // Unique index on alignment name
        createIndexSafe(AlignmentEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "alignments.name");

        // Unique index on background name
        createIndexSafe(BackgroundEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "backgrounds.name");

        // Unique index on feature name
        createIndexSafe(FeatureEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "features.name");

        // Unique index on subrace name
        createIndexSafe(SubraceEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "subraces.name");

        // Index on subrace raceName for lookups
        createIndexSafe(SubraceEntity.class, new Index().on("raceName", Sort.Direction.ASC), "subraces.raceName");

        // Unique index on spell name
        createIndexSafe(SpellEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "spells.name");

        // Index on spell level for lookups
        createIndexSafe(SpellEntity.class, new Index().on("minSlotLevel", Sort.Direction.ASC), "spells.minSlotLevel");

        // Unique index on equipment name
        createIndexSafe(EquipmentEntity.class, new Index().on("name", Sort.Direction.ASC).unique(), "equipment.name");

        // Index on equipment category for lookups
        createIndexSafe(EquipmentEntity.class, new Index().on("equipmentCategory", Sort.Direction.ASC), "equipment.equipmentCategory");

        log.info("MongoDB indexes creation initiated");
    }

    private void createIndexSafe(Class<?> entityClass, Index index, String indexDescription) {
        mongoTemplate.indexOps(entityClass).createIndex(index)
                .onErrorResume(e -> {
                    log.debug("Index {} already exists or conflict: {}", indexDescription, e.getMessage());
                    return Mono.empty();
                })
                .subscribe(result -> log.debug("Created index on {}: {}", indexDescription, result));
    }
}
