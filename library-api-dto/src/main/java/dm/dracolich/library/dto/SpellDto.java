package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dm.dracolich.library.dto.enums.AttackTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
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
    private Integer minSlotLevel;
    private Integer castingTime; // in hours, minutes or actions
    private Integer range;
    private Integer duration;
    private SpellTypeEnum spellType;
    private AttackTypeEnum attackType; //required if spellType is ATTACK
    private Set<DamageTypeEnum> damageTypes; //required if spellType is ATTACK
    private Map<Integer, Integer> valueAtSlotLevel;
}
