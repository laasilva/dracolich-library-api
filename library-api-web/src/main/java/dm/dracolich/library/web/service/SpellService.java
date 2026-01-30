package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpellService {
    Mono<SpellDto> fetchSpellByName(String name);
    Flux<SpellDto> searchSpellsByName(String name);
    Flux<SpellDto> fetchSpellsByFilters(Integer level, SpellTypeEnum type, SchoolTypeEnum school);
}
