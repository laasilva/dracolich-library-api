package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.SubraceDto;
import dm.dracolich.library.web.entity.SubraceEntity;
import dm.dracolich.library.web.mapper.SubraceMapper;
import dm.dracolich.library.web.repository.SubraceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SubraceServiceImpl implements  SubraceService {
    private final SubraceRepository repo;
    private final SubraceMapper mapper;


    @Override
    public Mono<SubraceDto> fetchSubraceByName(String name) {
        return repo.findByNameIgnoreCase(name)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<SubraceDto> searchSubracesByFilter(String name, String raceName) {
        SubraceEntity example = new SubraceEntity();

        if(name == null) {
            if(raceName != null)
                example.setRaceName(raceName);

            return repo.findAll(Example.of(example))
                    .map(mapper::entityToDto);
        }

        return repo.findAllByNameContainingIgnoreCase(name)
                .map(mapper::entityToDto);
    }
}
