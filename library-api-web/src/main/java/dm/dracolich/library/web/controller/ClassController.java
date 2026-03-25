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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    public Mono<? extends Page<?>> fetchAllClasses(@RequestParam(defaultValue = "true") boolean includeDetails,
                                                   @RequestParam(defaultValue = "false") boolean includeCustom,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        if(includeDetails)
            return service.fetchAllClassesDetailed(includeCustom, page, size);

        return service.fetchAllClasses(includeCustom, page, size);
    }

    @Operation(summary = "Fetch classes by name", description = "Returns one class by name")
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
    public Mono<? extends Page<?>> searchClassesByName(@RequestParam String name,
                                                       @RequestParam(defaultValue = "true") boolean includeDetails,
                                                       @RequestParam(defaultValue = "false") boolean includeCustom,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        if(includeDetails)
            return service.searchClassesByNameDetailed(name, includeCustom, page, size);

        return service.searchClassesByName(name, includeCustom, page, size);
    }

    @Operation(summary = "Create custom class", description = "Creates a new custom class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Class created successfully",
                    content = @Content(schema = @Schema(implementation = ClassDto.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClassDto> createClass(@RequestBody ClassDto classDto) {
        return service.createClass(classDto);
    }

}
