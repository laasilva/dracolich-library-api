package dm.dracolich.library.web.service;

import dm.dracolich.library.dto.enums.EntityEnum;
import dm.dracolich.library.web.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinary;

    private final ClassRepository classRepo;
    private final EquipmentRepository equipmentRepo;
    private final RaceRepository raceRepo;
    private final SpellRepository spellRepo;
    private final SubclassRepository subclassRepo;
    private final SubraceRepository subraceRepo;


    @Override
    public Mono<String> uploadImage(FilePart filePart, EntityEnum entityType, String name) {
        return switch (entityType) {
            case CLASS -> saveClassImage(filePart, name);
            case EQUIPMENT -> saveEquipmentImage(filePart, name);
            case RACE -> saveRaceImage(filePart, name);
            case SPELL -> saveSpellImage(filePart, name);
            case SUBCLASS -> saveSubclassImage(filePart, name);
            case SUBRACE -> saveSubraceImage(filePart, name);
            case null -> saveCommonImage(filePart);
        };
    }

    private Mono<String> saveClassImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "classes")
                .flatMap(url -> classRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return classRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveEquipmentImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "equipments")
                .flatMap(url -> equipmentRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return equipmentRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveRaceImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "races")
                .flatMap(url -> raceRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return raceRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveSpellImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "spells")
                .flatMap(url -> spellRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return spellRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveSubclassImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "subclasses")
                .flatMap(url -> subclassRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return subclassRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveSubraceImage(FilePart filePart, String name) {
        return cloudinary.upload(filePart, "subclasses")
                .flatMap(url -> subraceRepo.findByNameIgnoreCase(name)
                        .flatMap(entity -> {
                            entity.setImage(url);
                            return subraceRepo.save(entity);
                        })
                        .thenReturn(url));
    }

    private Mono<String> saveCommonImage(FilePart filePart) {
        return cloudinary.upload(filePart, "common");
    }
}

