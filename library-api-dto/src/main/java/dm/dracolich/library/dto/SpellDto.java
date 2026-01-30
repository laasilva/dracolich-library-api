package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.AttackTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.DiceTypeEnum;
import dm.dracolich.library.dto.enums.SchoolTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpellDto {
    @JsonIgnore
    private String id;
    private String name;
    private String description;
    private SchoolTypeEnum schoolType;
    private String[] actionDescription;
    private Integer minSlotLevel;
    private Integer castingTime;
    private Integer range;
    private Integer duration;
    private SpellTypeEnum spellType;
    private AttackTypeEnum attackType;
    private AbilityTypeEnum save;
    private Set<DamageTypeEnum> damageTypes;
    private DiceTypeEnum diceType;
    private String valueAtSlotLevelDescription;
    private Map<Integer, String> valueAtSlotLevel;
    private String slotIncreaseType;
}
