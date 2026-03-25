package dm.dracolich.library.web.repository.custom;

import dm.dracolich.library.web.entity.ClassEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClassCustomRepositoryImpl implements ClassCustomRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Page<ClassEntity>> findAllPaginated(boolean includeCustom, int page, int size) {
        Criteria criteria = new Criteria();

        if (!includeCustom)
            criteria.and("custom").is(false);

        Query query = Query.query(criteria);
        PageRequest pageRequest = PageRequest.of(page, size);

        return mongoTemplate.count(Query.query(criteria), ClassEntity.class)
                .flatMap(total -> mongoTemplate.find(query.with(pageRequest), ClassEntity.class)
                        .collectList()
                        .map(list -> new PageImpl<>(list, pageRequest, total)));
    }

    @Override
    public Mono<Page<ClassEntity>> searchByNamePaginated(String name, boolean includeCustom, int page, int size) {
        Criteria criteria = new Criteria();

        if (name != null && !name.isBlank())
            criteria.and("name").regex(name, "i");

        if (!includeCustom)
            criteria.and("custom").is(false);

        Query query = Query.query(criteria);
        PageRequest pageRequest = PageRequest.of(page, size);

        return mongoTemplate.count(Query.query(criteria), ClassEntity.class)
                .flatMap(total -> mongoTemplate.find(query.with(pageRequest), ClassEntity.class)
                        .collectList()
                        .map(list -> new PageImpl<>(list, pageRequest, total)));
    }
}
