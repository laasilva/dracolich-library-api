package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.dto.enums.ClassEnum;
import dm.dracolich.library.web.service.ClassService;
import dm.dracolich.library.web.service.SubclassService;
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
@RequestMapping("subclasses")
@Tag(name = "Subclasses")
@RequiredArgsConstructor
public class SubclassController {
    private final SubclassService service;

    @Operation(summary = "Fetch Subclasses by name", description = "Returns a subclass by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subclass fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SubclassDto> fetchSubclassByName(@PathVariable String name) {
        return service.fetchSubclassByName(name);
    }

    @Operation(summary = "Search Subclasses by filters", description = "Returns subclasses by search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subclass fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SubclassDto> searchSubclassesByName(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false, name = "class") ClassEnum className) {
        return service.searchSubclassesByFilter(name, className);
    }


}
