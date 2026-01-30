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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeDto {
    private String id;
    private String name;
    private String description;
    private Map<AbilityTypeEnum, Integer> abilityBonus;
}
