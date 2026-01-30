package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.web.entity.SpellEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface SpellMapper {
    SpellDto entityToDto(SpellEntity entity);
}
