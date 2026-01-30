package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.ClassLevelProgressionDto;
import dm.dracolich.library.dto.StartingEquipmentItemDto;
import dm.dracolich.library.dto.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document(collection = "classes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private Map<Integer, Set<SkillsEnum>> skillProficiencies; // and how many can be choosen
    private DiceTypeEnum hitDice;
    private Set<AbilityTypeEnum> savingThrows;
    private Set<ArmorTypeEnum> armorTraining;
    private Set<WeaponTypeEnum> weaponTraining;
    /**
     * Starting equipment options. Each set contains equipment items with quantities.
     * Equipment is referenced by name to look up from equipment collection.
     */
    private Set<StartingEquipmentItemDto> startingEquipmentA;
    private Set<StartingEquipmentItemDto> startingEquipmentB;
    private Set<StartingEquipmentItemDto> startingEquipmentC;
    private Set<ClassLevelProgressionDto> levelProgression;
    private boolean custom;
}
