package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "skills")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private Map<AbilityTypeEnum, Integer> abilityScore;
}
