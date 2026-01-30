package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dm.dracolich.library.dto.enums.*;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassDto {
    @JsonIgnore
    private String id;
    private String name;
    private String description;
    private Set<SubclassDto> subclasses;
    private Map<Integer, Set<SkillsEnum>> skillProficiencies;
    private DiceTypeEnum hitDice;
    private Set<AbilityTypeEnum> savingThrows;
    private Set<ArmorTypeEnum> armorTraining;
    private Set<WeaponTypeEnum> weaponTraining;
    private Set<StartingEquipmentItemDto> startingEquipmentA;
    private Set<StartingEquipmentItemDto> startingEquipmentB;
    private Set<StartingEquipmentItemDto> startingEquipmentC;
    private Set<ClassLevelProgressionDto> levelProgression;
    private boolean custom;
}
