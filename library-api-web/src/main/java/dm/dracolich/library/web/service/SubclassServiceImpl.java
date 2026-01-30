package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.web.mapper.SubclassMapper;
import dm.dracolich.library.web.repository.SubclassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubclassServiceImpl implements SubclassService {
    private final SubclassRepository repo;
    private final SubclassMapper mapper;

    @Override
    public Mono<SubclassDto> fetchSubclassByName(String name) {
        return repo.findByNameIgnoreCase(name)
                .map(mapper::entityToRecord);
    }

    @Override
    public Flux<SubclassDto> fetchSubclassesByClassName(String className) {
        return repo.findAllByClassNameIgnoreCase(className)
                .map(mapper::entityToRecord);
    }
}
