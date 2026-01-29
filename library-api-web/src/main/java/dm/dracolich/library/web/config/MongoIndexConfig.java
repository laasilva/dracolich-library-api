package dm.dracolich.library.web.config;

import dm.dracolich.library.web.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MongoIndexConfig {

    private final ReactiveMongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        log.info("Creating MongoDB indexes...");

        // Unique index on attribute name
        mongoTemplate.indexOps(AttributeEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on attributes.name: {}", result));

        // Unique index on race name
        mongoTemplate.indexOps(RaceEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on races.name: {}", result));

        // Unique index on class name
        mongoTemplate.indexOps(ClassEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on classes.name: {}", result));

        // Unique index on subclass name
        mongoTemplate.indexOps(SubclassEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on subclasses.name: {}", result));

        // Index on subclass classId for lookups
        mongoTemplate.indexOps(SubclassEntity.class).createIndex(new Index().on("classId", Sort.Direction.ASC))
                .subscribe(result -> log.debug("Created index on subclasses.classId: {}", result));

        // Unique index on alignment name
        mongoTemplate.indexOps(AlignmentEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on alignments.name: {}", result));

        // Unique index on background name
        mongoTemplate.indexOps(BackgroundEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on backgrounds.name: {}", result));

        // Unique index on feature name
        mongoTemplate.indexOps(FeatureEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on features.name: {}", result));

        // Unique index on subrace name
        mongoTemplate.indexOps(SubraceEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on subraces.name: {}", result));

        // Index on subrace raceId for lookups
        mongoTemplate.indexOps(SubraceEntity.class).createIndex(new Index().on("raceId", Sort.Direction.ASC))
                .subscribe(result -> log.debug("Created index on subraces.raceId: {}", result));

        // Unique index on spell name
        mongoTemplate.indexOps(SpellEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on spells.name: {}", result));

        // Index on spell level for lookups
        mongoTemplate.indexOps(SpellEntity.class).createIndex(new Index().on("minSlotLevel", Sort.Direction.ASC))
                .subscribe(result -> log.debug("Created index on spells.minSlotLevel: {}", result));

        // Unique index on equipment name
        mongoTemplate.indexOps(EquipmentEntity.class).createIndex(new Index().on("name", Sort.Direction.ASC).unique())
                .subscribe(result -> log.debug("Created index on equipment.name: {}", result));

        // Index on equipment category for lookups
        mongoTemplate.indexOps(EquipmentEntity.class).createIndex(new Index().on("equipmentCategory", Sort.Direction.ASC))
                .subscribe(result -> log.debug("Created index on equipment.equipmentCategory: {}", result));

        log.info("MongoDB indexes creation initiated");
    }
}
