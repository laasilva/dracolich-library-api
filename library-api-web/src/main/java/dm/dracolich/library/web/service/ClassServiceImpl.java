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
    public Flux<ClassResumedRecord> fetchAllClasses() {
        return repo.findAll()
                .map(mapper::entityToResumedRecord);
    }

    @Override
    public Flux<ClassDto> fetchAllClassesDetailed() {
        return repo.findAll()
                .flatMap(this::enrichWithSubclasses);
    }

    private Mono<ClassDto> enrichWithSubclasses(ClassEntity classEntity) {
        var mapped = mapper.entityToRecord(classEntity);

        return subclassRepo.findAllByClassNameIgnoreCase(classEntity.getName())
                .map(subclassMapper::entityToRecord)
                .collectList()
                .map(subclassesList -> {
                    mapped.setSubclasses(new HashSet<>(subclassesList));
                    return mapped;
                });
    }
}
