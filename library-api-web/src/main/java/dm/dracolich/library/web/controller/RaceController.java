package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.RaceDto;
import dm.dracolich.library.web.service.RaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("races")
@Tag(name = "Races")
@RequiredArgsConstructor
public class RaceController {
    private final RaceService service;

    @Operation(summary = "Fetch all races", description = "Returns all races")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Races fetched successfully",
                    content = @Content(schema = @Schema(implementation = RaceDto.class)))
    })
    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> fetchAllRaces(@RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.fetchAllRacesDetailed();

        return service.fetchAllRaces();
    }

    @Operation(summary = "Fetch all races", description = "Returns one race by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Race fetched successfully",
                    content = @Content(schema = @Schema(implementation = RaceDto.class)))
    })
    @GetMapping(path = {"/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<?> fetchRaceByName(@PathVariable String name,
                                    @RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.fetchRaceByName(name);

        return service.fetchRaceDetailed(name);
    }

    @Operation(summary = "Search races by name", description = "Returns races that match name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Races fetched successfully",
                    content = @Content(schema = @Schema(implementation = RaceDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> searchRacesByName(@RequestParam String name,
                                       @RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.searchRacesByName(name);

        return service.seachRacesByNameDetailed(name);
    }
}
