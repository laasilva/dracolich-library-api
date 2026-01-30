package dm.dracolich.library.dto.records;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;

import java.util.Map;

public record SkillRecord(String id,
                          String name,
                          String description,
                          Map<AbilityTypeEnum, Integer> abilityScore) {}
