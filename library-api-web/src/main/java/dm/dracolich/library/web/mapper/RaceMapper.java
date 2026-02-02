package dm.dracolich.library.web.mapper;

import dm.dracolich.library.dto.RaceDto;
import dm.dracolich.library.dto.SpellDto;
import dm.dracolich.library.dto.records.RaceResumedRecord;
import dm.dracolich.library.web.entity.RaceEntity;
import dm.dracolich.library.web.entity.SpellEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, uses = {SpellMapper.class})
public interface RaceMapper {
    RaceDto entityToDto(RaceEntity entity);
    RaceResumedRecord entityToResumedRecord(RaceEntity entity);

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
