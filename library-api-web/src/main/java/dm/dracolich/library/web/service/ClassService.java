package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.records.ClassResumedRecord;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface ClassService {
    Mono<ClassResumedRecord> fetchClassByName(String name);
    Mono<ClassDto> fetchClassByNameDetailed(String name);
    Mono<Page<ClassResumedRecord>> fetchAllClasses(boolean includeCustom, int page, int size);
    Mono<Page<ClassDto>> fetchAllClassesDetailed(boolean includeCustom, int page, int size);
    Mono<Page<ClassResumedRecord>> searchClassesByName(String name, boolean includeCustom, int page, int size);
    Mono<Page<ClassDto>> searchClassesByNameDetailed(String name, boolean includeCustom, int page, int size);
    Mono<ClassDto> createClass(ClassDto classDto);
}
