package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.mapper.SpellMapper;
import dm.dracolich.library.web.repository.SpellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements SpellService {
    private final SpellRepository repo;
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
        return repo.findByFilters(name, level, type, school, damageTypes, save, page, size)
                .map(p -> p.map(mapper::entityToDto));
    }

    @Override
    public Mono<Page<SpellDto>> fetchSpellsByFilters(Integer level, SpellTypeEnum type,
                                                     SchoolTypeEnum school, Set<DamageTypeEnum> damageTypes,
                                                     AbilityTypeEnum save, int page, int size) {
        return repo.findByFilters(null, level, type, school, damageTypes, save, page, size)
                .map(p -> p.map(mapper::entityToDto));
    }
}
