package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("spells")
@Tag(name = "Spells")
@RequiredArgsConstructor
public class SpellController {
    private final SpellService service;

    @Operation(summary = "Fetch spell by Id", description = "Returns all spells by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SpellDto> fetchSpellById(@PathVariable String id) {
        return service.fetchSpellById(id);
    }

    @Operation(summary = "Search all spells", description = "Returns all spells by filters, type, school or level (0 - cantrip, 1-9 - spell level)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {"/filter"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Page<SpellDto>> fetchSpellsFilter(@RequestParam(required = false) Integer level,
                                      @RequestParam(required = false) SpellTypeEnum type,
                                      @RequestParam(required = false) SchoolTypeEnum school,
                                      @RequestParam(required = false) Set<DamageTypeEnum> damageTypes,
                                      @RequestParam(required = false) AbilityTypeEnum save,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        return service.fetchSpellsByFilters(level, type, school, damageTypes, save, page, size);
    }

    @Operation(summary = "Search all spells by name", description = "Returns spells by search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spells fetched successfully",
                    content = @Content(schema = @Schema(implementation = SpellDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Page<SpellDto>> searchSpellsByName(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer level,
                                      @RequestParam(required = false) SpellTypeEnum type,
                                      @RequestParam(required = false) SchoolTypeEnum school,
                                      @RequestParam(required = false) Set<DamageTypeEnum> damageTypes,
                                      @RequestParam(required = false) AbilityTypeEnum save,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        return service.searchSpellsByName(name, level, type, school, damageTypes, save, page, size);
    }
}
