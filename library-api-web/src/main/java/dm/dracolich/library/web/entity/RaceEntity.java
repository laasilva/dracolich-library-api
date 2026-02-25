package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
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
    private String image;
    private SizeEnum size;
    private Integer speed;
    private Set<AttributeEntity> raceAttributes;
    private Map<Integer, List<SpellEntity>> cantripsAndSpells;
    private Map<AbilityTypeEnum, Integer> abilityBonus; // ability type and bonus points to be applied
    private boolean custom;
}
