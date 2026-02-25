package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.enums.EntityEnum;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<String> uploadImage(FilePart filePart, EntityEnum entityType, String name);
}
