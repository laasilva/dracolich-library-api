package dm.dracolich.library.web.repository.custom;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.entity.SpellEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.Set;

@RequiredArgsConstructor
public class SpellCustomRepositoryImpl implements SpellCustomRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Page<SpellEntity>> findByFilters(String name, Integer level, SpellTypeEnum type,
                                                  SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes,
                                                  AbilityTypeEnum save, int page, int size) {
        Criteria criteria = new Criteria();

        if (name != null && !name.isBlank())
            criteria.and("name").regex(name, "i");

        if (level != null)
            criteria.and("minSlotLevel").is(level);

        if (type != null)
            criteria.and("spellType").is(type);

        if (school != null)
            criteria.and("schoolType").is(school);

        if (damageTypes != null && !damageTypes.isEmpty())
            criteria.and("damageTypes").all(damageTypes);

        if (save != null)
            criteria.and("save").is(save);

        Query query = Query.query(criteria);
        PageRequest pageRequest = PageRequest.of(page, size);

        return mongoTemplate.count(Query.query(criteria), SpellEntity.class)
                .flatMap(total -> mongoTemplate.find(query.with(pageRequest), SpellEntity.class)
                        .collectList()
                        .map(list -> new PageImpl<>(list, pageRequest, total)));
    }
}
