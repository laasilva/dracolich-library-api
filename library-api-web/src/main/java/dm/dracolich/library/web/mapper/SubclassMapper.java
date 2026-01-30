package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.SubclassDto;
import dm.dracolich.library.web.entity.SubclassEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface SubclassMapper {
    SubclassDto entityToRecord(SubclassEntity entity);
}
