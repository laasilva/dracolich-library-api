package dm.dracolich.library.web.repository.custom;

import dm.dracolich.library.web.entity.ClassEntity;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface ClassCustomRepository {
    Mono<Page<ClassEntity>> findAllPaginated(boolean includeCustom, int page, int size);
    Mono<Page<ClassEntity>> searchByNamePaginated(String name, boolean includeCustom, int page, int size);
}
