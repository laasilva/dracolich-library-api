package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.RaceDto;
import dm.dracolich.library.dto.records.RaceResumedRecord;
import dm.dracolich.library.web.entity.RaceEntity;
import dm.dracolich.library.web.mapper.RaceMapper;
import dm.dracolich.library.web.mapper.SubraceMapper;
import dm.dracolich.library.web.repository.RaceRepository;
import dm.dracolich.library.web.repository.SubraceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final RaceRepository repo;
    private final SubraceRepository subraceRepo;
    private final RaceMapper mapper;
    private final SubraceMapper subraceMapper;

    @Override
    public Flux<RaceResumedRecord> fetchAllRaces() {
        return repo.findAll()
            .map(mapper::entityToResumedRecord);
    }

    @Override
    public Flux<RaceDto> fetchAllRacesDetailed() {
        return repo.findAll()
                .flatMap(this::enrichWithSubraces);
    }

    @Override
    public Mono<RaceResumedRecord> fetchRaceByName(String name) {
        return repo.findByNameIgnoreCase(name)
                .map(mapper::entityToResumedRecord);
    }

    @Override
    public Mono<RaceDto> fetchRaceDetailed(String name) {
        return repo.findByNameIgnoreCase(name)
                .flatMap(this::enrichWithSubraces);
    }

    @Override
    public Flux<RaceResumedRecord> searchRacesByName(String name) {
        return repo.findAllByNameContainingIgnoreCase(name)
                .map(mapper::entityToResumedRecord);
    }

    @Override
    public Flux<RaceDto> seachRacesByNameDetailed(String name) {
        return repo.findAllByNameContainingIgnoreCase(name)
                .flatMap(this::enrichWithSubraces);
    }


    private Mono<RaceDto> enrichWithSubraces(RaceEntity raceEntity) {
        var mapped = mapper.entityToDto(raceEntity);

        return subraceRepo.findAllByRaceNameIgnoreCase(raceEntity.getName())
                .map(subraceMapper::entityToDto)
                .collectList()
                .map(subclassesList -> {
                    mapped.setSubraces(new HashSet<>(subclassesList));
                    return mapped;
                });
    }
}
