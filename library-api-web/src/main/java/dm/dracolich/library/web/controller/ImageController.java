package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.enums.EntityEnum;
import dm.dracolich.library.web.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("images")
@Tag(name = "Images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(path = {""}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> uploadImage(@RequestPart("file") FilePart filePart,
                                    @RequestParam(name = "entityType", required = false) EntityEnum entityType,
                                    @RequestParam(name = "name", required = false) String name) {
        return imageService.uploadImage(filePart, entityType, name);
    }
}
