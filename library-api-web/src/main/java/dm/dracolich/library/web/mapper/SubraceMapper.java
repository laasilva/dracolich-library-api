package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.SubraceDto;
import dm.dracolich.library.web.entity.SpellEntity;
import dm.dracolich.library.web.entity.SubraceEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, uses = {SpellMapper.class})
public interface SubraceMapper {
    SubraceDto entityToDto(SubraceEntity entity);

    default Map<Integer, List<SpellDto>> mapCantripsAndSpells(Map<Integer, List<SpellEntity>> cantripsAndSpells) {
        if (cantripsAndSpells == null) {
            return null;
        }
        return cantripsAndSpells.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::spellEntityToDto)
                                .collect(Collectors.toList())
                ));
    }

    SpellDto spellEntityToDto(SpellEntity entity);
}
