package dm.dracolich.library.web.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dm.dracolich.forge.exception.ResponseException;
import dm.dracolich.forge.error.ApiError;
import dm.dracolich.forge.error.ErrorSeverity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static dm.dracolich.forge.error.ErrorCodes.DMD010;
import static dm.dracolich.forge.error.ErrorCodes.DMD011;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

    private static final String UPLOAD_FOLDER = "dracolich-library";

    private final Cloudinary cloudinary;

    /**
     * Uploads an image to Cloudinary and returns the secure URL.
     *
     * @param filePart the multipart file from the request
     * @param folder   subfolder within the base upload folder (e.g. "classes", "spells")
     * @return Mono with the secure URL of the uploaded image
     */
    public Mono<String> upload(FilePart filePart, String folder) {
        return saveToTempFile(filePart)
                .flatMap(tempPath -> uploadToCloudinary(tempPath, folder));
    }

    /**
     * Deletes an image from Cloudinary by its public ID.
     *
     * @param imageUrl the secure URL of the image to delete
     * @return Mono completing when the deletion is done
     */
    public Mono<Void> delete(String imageUrl) {
        String publicId = extractPublicId(imageUrl);
        return Mono.fromCallable(() -> cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap()))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(_ -> log.debug("Deleted image from Cloudinary: {}", publicId))
                .doOnError(e -> {
                    throw new ResponseException(format(DMD011.getMessage(), e.getMessage()), List.of(new ApiError(DMD011, ErrorSeverity.ERROR,
                            "CloudinaryService::upload")), HttpStatus.INTERNAL_SERVER_ERROR);
                })
                .then();
    }

    private Mono<Path> saveToTempFile(FilePart filePart) {
        return Mono.fromCallable(() -> Files.createTempFile("upload-", "-" + filePart.filename()))
                .flatMap(tempPath -> filePart.transferTo(tempPath).thenReturn(tempPath));
    }

    private Mono<String> uploadToCloudinary(Path tempPath, String folder) {
        return Mono.fromCallable(() -> {
                    Map<?, ?> result = cloudinary.uploader().upload(tempPath.toFile(), Map.of(
                            "folder", UPLOAD_FOLDER + "/" + folder,
                            "resource_type", "image"
                    ));
                    return (String) result.get("secure_url");
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(url -> log.debug("Uploaded image to Cloudinary: {}", url))
                .doOnError(e -> {
                    throw new ResponseException(format(DMD010.getMessage(), e.getMessage()), List.of(new ApiError(DMD010, ErrorSeverity.ERROR,
                            "CloudinaryService::upload")), HttpStatus.INTERNAL_SERVER_ERROR);
                })
                .doFinally(signal -> deleteTempFile(tempPath));
    }

    private String extractPublicId(String imageUrl) {
        // URL format: https://res.cloudinary.com/<cloud>/image/upload/v123/folder/file.ext
        String[] parts = imageUrl.split("/upload/");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + imageUrl);
        }
        String pathWithVersion = parts[1];
        // Remove version prefix (v123456789/)
        String path = pathWithVersion.replaceFirst("v\\d+/", "");
        // Remove file extension
        return path.substring(0, path.lastIndexOf('.'));
    }

    private void deleteTempFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.warn("Failed to delete temp file: {}", path, e);
        }
    }
}
