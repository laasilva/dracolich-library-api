package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.RaceDto;
import dm.dracolich.library.dto.records.RaceResumedRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RaceService {
    Flux<RaceResumedRecord> fetchAllRaces();
    Flux<RaceDto> fetchAllRacesDetailed();
    Mono<RaceResumedRecord> fetchRaceByName(String name);
    Mono<RaceDto> fetchRaceDetailed(String name);
    Flux<RaceResumedRecord> searchRacesByName(String name);
    Flux<RaceDto> seachRacesByNameDetailed(String name);
}
