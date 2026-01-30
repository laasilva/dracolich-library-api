package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.AttributeDto;
import dm.dracolich.library.web.entity.AttributeEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD)
public interface AttributeMapper {
    AttributeDto entityToDto(AttributeEntity entity);
}
