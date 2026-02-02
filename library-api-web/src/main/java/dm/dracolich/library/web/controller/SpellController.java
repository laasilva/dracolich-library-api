package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.service.SpellService;
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
@RequestMapping("spells")
@Tag(name = "Spells")
@RequiredArgsConstructor
public class SpellController {
    private final SpellService service;

    @Operation(summary = "Fetch all spells by name", description = "Returns all spells by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SpellDto> fetchSpellsByName(@RequestParam String name) {
        return service.fetchSpellByName(name);
    }

    @Operation(summary = "Search all spells", description = "Returns all spells by filters, type, school or level (0 - cantrip, 1-9 - spell level)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {"/filter"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SpellDto> fetchSpellsFilter(@RequestParam(required = false) Integer level,
                                            @RequestParam(required = false) SpellTypeEnum type,
                                            @RequestParam(required = false) SchoolTypeEnum school) {
        return service.fetchSpellsByFilters(level, type, school);
    }

    @Operation(summary = "Search all spells by name", description = "Returns spells by search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SpellDto> searchSpellsByName(@RequestParam String name) {
        return service.searchSpellsByName(name);
    }
}
