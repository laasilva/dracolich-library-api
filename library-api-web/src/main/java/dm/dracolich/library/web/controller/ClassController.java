package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.web.service.ClassService;
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
@RequestMapping("classes")
@Tag(name = "Classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService service;

    @Operation(summary = "Fetch all classes", description = "Returns all classes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> fetchAllClasses(@RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.fetchAllClassesDetailed();

        return service.fetchAllClasses();
    }

    @Operation(summary = "Fetch all classes", description = "Returns one class by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<?> fetchClassByName(@PathVariable String name,
                                                @RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.fetchClassByNameDetailed(name);

        return service.fetchClassByName(name);
    }

    @Operation(summary = "Search classes by name", description = "Returns classes that match name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes fetched successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @GetMapping(path = {"/search"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> searchClassesByName(@RequestParam String name,
                                       @RequestParam(defaultValue = "true") boolean includeDetails) {
        if(includeDetails)
            return service.searchClassesByNameDetailed(name);

        return service.searchClassesByName(name);
    }


}
