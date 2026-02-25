package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface SpellService {
    Mono<SpellDto> fetchSpellById(String name);
    Mono<Page<SpellDto>> searchSpellsByName(String name, Integer level, SpellTypeEnum type, SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes, AbilityTypeEnum save, int page, int size);
    Mono<Page<SpellDto>> fetchSpellsByFilters(Integer level, SpellTypeEnum type, SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes, AbilityTypeEnum save, int page, int size);
}
