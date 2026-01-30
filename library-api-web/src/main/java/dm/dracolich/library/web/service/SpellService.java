package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpellService {
    Flux<SpellDto> fetchAllSpells();
    Flux<SpellDto> fetchSpellsByLevel(Integer level);
    Flux<SpellDto> fetchSpellsByType(SpellTypeEnum spellType);
    Mono<SpellDto> fetchSpellByName(String name);
}
