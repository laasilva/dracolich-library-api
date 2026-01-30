package dm.dracolich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * Represents what a class gains at a specific level.
 * Contains features unlocked and class-specific scaling values.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassLevelProgressionDto {
    private Integer level;

    private Set<String> featureNames;  // features gained at this level

    /**
     * Class-specific scaling values at this level. Supports:
     * - Integers: {"rages": 2, "cantrips": 3}
     * - Strings: {"bardicDie": "1d6", "slotLevel": "2nd"}
     * - Nested maps: {"spellSlots": {"1": 4, "2": 3, "3": 2}}
     *
     * Examples:
     * - Barbarian: {"rages": 3, "rageDamage": 2, "weaponMastery": 2}
     * - Bard: {"bardicDie": "1d6", "cantrips": 2, "preparedSpells": 6, "spellSlots": {"1": 4, "2": 2}}
     * - Warlock: {"invocations": 2, "cantrips": 2, "preparedSpells": 4, "spellSlots": 2, "slotLevel": "2nd"}
     */
    private Map<String, Object> scalingValues;
}