package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.entity.SpellEntity;
import dm.dracolich.library.web.mapper.SpellMapper;
import dm.dracolich.library.web.repository.SpellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements SpellService {
    private final SpellRepository repo;
    private final ReactiveMongoTemplate mongoTemplate;
    private final SpellMapper mapper;

    @Override
    public Mono<SpellDto> fetchSpellById(String id) {
        return repo.findById(id)
                .map(mapper::entityToDto);
    }

    @Override
    public Mono<Page<SpellDto>> searchSpellsByName(String name, Integer level, SpellTypeEnum type,
                                                    SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes,
                                                    AbilityTypeEnum save, int page, int size) {
        Criteria criteria = buildFilterCriteria(level, type, school, damageTypes, save);

        if (name != null && !name.isBlank())
            criteria.and("name").regex(name, "i");

        return executePagedQuery(criteria, page, size);
    }

    @Override
    public Mono<Page<SpellDto>> fetchSpellsByFilters(Integer level, SpellTypeEnum type,
                                                     SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes,
                                                     AbilityTypeEnum save, int page, int size) {
        Criteria criteria = buildFilterCriteria(level, type, school, damageTypes, save);
        return executePagedQuery(criteria, page, size);
    }

    private Criteria buildFilterCriteria(Integer level, SpellTypeEnum type, SchoolTypeEnum school,
                                         Set<DamageTypeEnum> damageTypes, AbilityTypeEnum save) {
        Criteria criteria = new Criteria();

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

        return criteria;
    }

    private Mono<Page<SpellDto>> executePagedQuery(Criteria criteria, int page, int size) {
        Query query = Query.query(criteria);
        PageRequest pageRequest = PageRequest.of(page, size);

        return mongoTemplate.count(Query.query(criteria), SpellEntity.class)
                .flatMap(total -> mongoTemplate.find(query.with(pageRequest), SpellEntity.class)
                        .map(mapper::entityToDto)
                        .collectList()
                        .map(list -> new PageImpl<>(list, pageRequest, total)));
    }
}
