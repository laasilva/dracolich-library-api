package dm.dracolich.library.web.repository.custom;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.entity.SpellEntity;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface SpellCustomRepository {
    Mono<Page<SpellEntity>> findByFilters(String name, Integer level, SpellTypeEnum type,
                                          SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes,
                                          AbilityTypeEnum save, int page, int size);
}
