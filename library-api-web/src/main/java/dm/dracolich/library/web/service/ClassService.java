package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.records.ClassResumedRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassService {
    Mono<ClassResumedRecord> fetchClassByName(String name);
    Mono<ClassDto> fetchClassByNameDetailed(String name);
    Flux<ClassResumedRecord> fetchAllClasses();
    Flux<ClassDto> fetchAllClassesDetailed();
}
