package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto {
    private String id;
    private String name;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<AbilityTypeEnum, Integer> abilityBonus;
}
