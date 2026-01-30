package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.entity.SpellEntity;
import dm.dracolich.library.web.mapper.SpellMapper;
import dm.dracolich.library.web.repository.SpellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements SpellService {
    private final SpellRepository repo;
    private final SpellMapper mapper;

    @Override
    public Mono<SpellDto> fetchSpellByName(String name) {
        return repo.findByName(name)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SpellDto> searchSpellsByName(String name) {
        return repo.findByNameContainingIgnoreCase(name)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SpellDto> fetchSpellsByFilters(Integer level, SpellTypeEnum type, SchoolTypeEnum school) {
        SpellEntity example = new SpellEntity();

        if(level != null)
            example.setMinSlotLevel(level);

        if(type != null)
            example.setSpellType(type);

        if(school != null)
            example.setSchoolType(school);

        return repo.findAll(Example.of(example))
                .map(mapper::entityToDto);
    }
}
