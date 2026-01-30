package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.dto.enums.ClassEnum;
import dm.dracolich.library.web.entity.SubclassEntity;
import dm.dracolich.library.web.mapper.SubclassMapper;
import dm.dracolich.library.web.repository.SubclassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubclassServiceImpl implements SubclassService {
    private final SubclassRepository repo;
    private final SubclassMapper mapper;

    @Override
    public Mono<SubclassDto> fetchSubclassByName(String name) {
        return repo.findByNameIgnoreCase(name)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SubclassDto> searchSubclassesByFilter(String name, ClassEnum className) {
        SubclassEntity example = new SubclassEntity();

        if(name == null) {
            if(className != null)
                example.setClassName(className.name().toLowerCase(Locale.ROOT));

            return repo.findAll(Example.of(example))
                    .map(mapper::entityToDto);
        }

        return repo.findAllByNameContainingIgnoreCase(name, Example.of(example))
                .map(mapper::entityToDto);
    }
}
