package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.mapper.SpellMapper;
import dm.dracolich.library.web.repository.SpellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements  SpellService {
    private final SpellRepository repo;
    private final SpellMapper mapper;

    @Override
    public Flux<SpellDto> fetchAllSpells() {
        return repo.findAll()
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SpellDto> fetchSpellsByLevel(Integer level) {
        return repo.findByMinSlotLevel(level)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SpellDto> fetchSpellsByType(SpellTypeEnum spellType) {
        return repo.findBySpellType(spellType)
                .map(mapper::entityToDto);
    }

    @Override
    public Mono<SpellDto> fetchSpellByName(String name) {
        return repo.findByName(name)
                .map(mapper::entityToDto);
    }
}
