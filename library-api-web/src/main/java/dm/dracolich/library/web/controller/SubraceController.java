package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.dto.SubraceDto;
import dm.dracolich.library.dto.enums.ClassEnum;
import dm.dracolich.library.web.service.SubraceService;
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
@RequestMapping("subraces")
@Tag(name = "Subraces")
@RequiredArgsConstructor
public class SubraceController {
    private final SubraceService service;

    @Operation(summary = "Fetch Subraces by name", description = "Returns a Subrace by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subraces fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SubraceDto> fetchSubracesByName(@PathVariable String name) {
        return service.fetchSubraceByName(name);
    }

    @Operation(summary = "Search Subraces by filters", description = "Returns subraces by search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subraces fetched successfully",
                    content = @Content(schema = @Schema(implementation = SubraceDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SubraceDto> searchSubracesByName(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false, name = "race") String race) {
        return service.searchSubracesByFilter(name, race);
    }
}
