package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.AttackTypeEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document(collection = "spells")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpellEntity {
    @Id
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
    private Map<Integer, Integer> valueAtSlotLevel; // damage or heal value
}
