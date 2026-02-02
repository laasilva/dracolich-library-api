package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceDto {
    private String name;
    private SizeEnum size;
    private Integer speed;
    private Set<AttributeDto> raceAttributes;
    private Map<Integer, List<SpellDto>> cantripsAndSpells;
    private Map<AbilityTypeEnum, Integer> abilityBonus; // ability type and bonus points to be applied
    private Set<SubraceDto> subraces;
    private boolean custom;
}
