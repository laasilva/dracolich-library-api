package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document(collection = "races")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaceEntity {
    @Id
    private String id;
    private String name;
    private Set<AttributeEntity> raceAttributes;
    private Map<AbilityTypeEnum, Integer> abilityBonus; // ability type and bonus points to be applied
    private boolean custom;
}
