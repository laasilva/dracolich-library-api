package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubraceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubraceService {
    Mono<SubraceDto> fetchSubraceByName(String name);
    Flux<SubraceDto> searchSubracesByFilter(String name, String raceName);
}
