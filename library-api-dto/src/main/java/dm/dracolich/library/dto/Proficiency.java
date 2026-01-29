package dm.dracolich.library.dto;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proficiency {
    private String id;
    private String name;
    private AbilityTypeEnum proficiencyType;
    private Integer proficiencyBonus;
}
