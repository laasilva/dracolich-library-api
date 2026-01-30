package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.SubclassDto;
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

    @Operation(summary = "Fetch Subclasses by Class", description = "Returns all subclasses for a given class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subclasses fetched successfully",
                    content = @Content(schema = @Schema(implementation = SubclassDto.class)))
    })
    @GetMapping(path = {"/class/{class}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SubclassDto> fetchSubclassesByClassName(@PathVariable(name = "class") String className) {
        return service.fetchSubclassesByClassName(className);
    }

    @Operation(summary = "Fetch Subclasses by name", description = "Returns a subclass by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subclass fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SubclassDto> fetchSubclassByName(@PathVariable String name) {
        return service.fetchSubclassByName(name);
    }

    @Operation(summary = "Search Subclasses by name", description = "Returns subclasses by search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subclasses fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SubclassDto> searchSubclassesByName(@RequestParam String name) {
        return service.searchSubclassesByName(name);
    }


}
