package dm.dracolich.library.dto.records;

import dm.dracolich.library.dto.AttributeDto;
import dm.dracolich.library.dto.enums.CoinEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.EquipmentCategoryEnum;

import java.util.Map;
import java.util.Set;

public record EquipmentRecord(String id,
                              String name,
                              String description,
                              EquipmentCategoryEnum equipmentCategory,
                              Integer weight,
                              Map<CoinEnum, Integer> price,
                              SkillRecord skillType,
                              Map<DamageTypeEnum, Integer> damageTypeAndBonus,
                              Map<DamageTypeEnum, Integer> damageDisadvantages,
                              Set<AttributeDto> attributes,
                              Set<String> otherAttributes) {}
