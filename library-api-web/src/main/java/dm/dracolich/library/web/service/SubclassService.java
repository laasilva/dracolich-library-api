package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubclassDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubclassService {
    Mono<SubclassDto> fetchSubclassByName(String name);
    Flux<SubclassDto> fetchSubclassesByClassName(String className);
}
