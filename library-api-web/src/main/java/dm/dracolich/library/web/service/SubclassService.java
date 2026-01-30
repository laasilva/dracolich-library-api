package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.dto.enums.ClassEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubclassService {
    Mono<SubclassDto> fetchSubclassByName(String name);
    Flux<SubclassDto> searchSubclassesByFilter(String name, ClassEnum className);
}
