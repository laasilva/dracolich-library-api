package dm.dracolich.library.dto.records;

import dm.dracolich.library.dto.enums.*;

public record ClassResumedRecord(String name,
                                 String description,
                                 DiceTypeEnum hitDice,
                                 boolean custom) {
}
