package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.SpellDto;
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

    @Operation(summary = "Fetch all spells", description = "Returns all spells")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SpellDto> fetchAllClasses() {
        return service.fetchAllSpells();
    }

    @Operation(summary = "Fetch all spells by level", description = "Returns all spells by level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/level/{level}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SpellDto> fetchSpellsByLevel(@PathVariable Integer level) {
        return service.fetchSpellsByLevel(level);
    }

    @Operation(summary = "Fetch all spells by type", description = "Returns all spells by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/type/{type}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SpellDto> fetchSpellsByType(@PathVariable SpellTypeEnum type) {
        return service.fetchSpellsByType(type);
    }

    @Operation(summary = "Fetch all spells by name", description = "Returns all spells by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/name/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SpellDto> fetchSpellsByName(@PathVariable String name) {
        return service.fetchSpellByName(name);
    }
}
