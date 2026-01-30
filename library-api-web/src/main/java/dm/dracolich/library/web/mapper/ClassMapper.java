package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.records.ClassResumedRecord;
import dm.dracolich.library.web.entity.ClassEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ClassMapper {
    ClassDto entityToRecord(ClassEntity entity);
    ClassResumedRecord entityToResumedRecord(ClassEntity entity);
}
