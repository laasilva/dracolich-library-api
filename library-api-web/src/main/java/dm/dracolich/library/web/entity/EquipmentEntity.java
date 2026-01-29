package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.enums.CoinEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.EquipmentCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document(collection = "equipment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private EquipmentCategoryEnum equipmentCategory;
    private Integer weight;
    private Map<CoinEnum, Integer> price;
    private SkillEntity skillType;
    private Map<DamageTypeEnum, Integer> damageTypeAndBonus;
    private Map<DamageTypeEnum, Integer> damageDisadvantages;
    private Set<AttributeEntity> attributes;
    private Set<String> otherAttributes;
}
