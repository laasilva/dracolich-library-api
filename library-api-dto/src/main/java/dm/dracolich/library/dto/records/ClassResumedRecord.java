package dm.dracolich.library.dto.records;

import dm.dracolich.library.dto.enums.*;

import java.util.Set;

public record ClassResumedRecord(String name,
                                 String description,
                                 String image,
                                 DiceTypeEnum hitDice,
                                 Set<AbilityTypeEnum> savingThrows,
                                 boolean custom) {
}
