package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.*;
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
    private SchoolTypeEnum schoolType;
    private String[] actionDescription; // if spell doesn't have damage types or values to be attributed, but has precise description, ex: "Spell: Alarm = Audible Alarm. The alarm produces the sound of a handbell for 10 seconds within 60 feet of the warded area. Mental Alarm. You are alerted by a mental ping if you are within 1 mile of the warded area. This ping awakens you if you’re asleep."
    private Integer minSlotLevel;
    private Integer castingTime; // in actions, 0 if instantaneous
    private Integer range; // in feet, 0 if self or touch
    private Integer duration; // in hours, 0 if instantaneous
    private SpellTypeEnum spellType;
    private AttackTypeEnum attackType;
    private AbilityTypeEnum save;
    private Set<DamageTypeEnum> damageTypes; // damage/effect
    private DiceTypeEnum diceType;
    private String valueAtSlotLevelDescription;
    private Map<Integer, String> valueAtSlotLevel;
    private String slotIncreaseType;
    // valueAtSlotLevel maps level/slot -> damage value
    // Cantrip ex: {5: "2d6", 11: "3d6", 17: "4d6"} - damage at character levels 5, 11, 17
    // Leveled spell ex: {2: "4d6", 3: "5d6", 4: "6d6"} - damage at spell slots 2, 3, 4
}
