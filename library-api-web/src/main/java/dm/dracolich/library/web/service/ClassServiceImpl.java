package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.records.ClassResumedRecord;
import dm.dracolich.library.web.entity.ClassEntity;
import dm.dracolich.library.web.mapper.ClassMapper;
import dm.dracolich.library.web.mapper.SubclassMapper;
import dm.dracolich.library.web.repository.ClassRepository;
import dm.dracolich.library.web.repository.SubclassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository repo;
    private final ClassMapper mapper;

    private final SubclassRepository subclassRepo;
    private final SubclassMapper subclassMapper;

    @Override
    public Mono<ClassResumedRecord> fetchClassByName(String name) {
        return repo.findByNameIgnoreCase(name)
                .map(mapper::entityToResumedRecord);
    }

    @Override
    public Mono<ClassDto> fetchClassByNameDetailed(String name) {
        return repo.findByNameIgnoreCase(name)
                .flatMap(this::enrichWithSubclasses);
    }

    @Override
    public Mono<Page<ClassResumedRecord>> fetchAllClasses(boolean includeCustom, int page, int size) {
        return repo.findAllPaginated(includeCustom, page, size)
                .map(p -> p.map(mapper::entityToResumedRecord));
    }

    @Override
    public Mono<Page<ClassDto>> fetchAllClassesDetailed(boolean includeCustom, int page, int size) {
        return repo.findAllPaginated(includeCustom, page, size)
                .flatMap(p -> Flux.fromIterable(p.getContent())
                        .flatMap(this::enrichWithSubclasses)
                        .collectList()
                        .map(list -> new PageImpl<>(list, p.getPageable(), p.getTotalElements())));
    }

    @Override
    public Mono<Page<ClassResumedRecord>> searchClassesByName(String name, boolean includeCustom, int page, int size) {
        return repo.searchByNamePaginated(name, includeCustom, page, size)
                .map(p -> p.map(mapper::entityToResumedRecord));
    }

    @Override
    public Mono<Page<ClassDto>> searchClassesByNameDetailed(String name, boolean includeCustom, int page, int size) {
        return repo.searchByNamePaginated(name, includeCustom, page, size)
                .flatMap(p -> Flux.fromIterable(p.getContent())
                        .flatMap(this::enrichWithSubclasses)
                        .collectList()
                        .map(list -> new PageImpl<>(list, p.getPageable(), p.getTotalElements())));
    }

    @Override
    public Mono<ClassDto> createClass(ClassDto classDto) {
        var entity = mapper.dtoToEntity(classDto);
        entity.setId(null);
        entity.setCustom(true);
        return repo.save(entity)
                .map(mapper::entityToDto);
    }

    private Mono<ClassDto> enrichWithSubclasses(ClassEntity classEntity) {
        var mapped = mapper.entityToDto(classEntity);

        return subclassRepo.findAllByClassNameIgnoreCase(classEntity.getName())
                .map(subclassMapper::entityToDto)
                .collectList()
                .map(subclassesList -> {
                    mapped.setSubclasses(new HashSet<>(subclassesList));
                    return mapped;
                });
    }
}
