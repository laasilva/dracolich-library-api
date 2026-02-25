package dm.dracolich.library.dto.enums;

import lombok.Getter;

@Getter
public enum AbilityTypeEnum {
    STR("Strength"),
    DEX("Dexterity"),
    CON("Constitution"),
    INT("Intelligence"),
    WIS("Wisdom"),
    CHA("Charisma");

    private final String name;

    AbilityTypeEnum(String name) {
        this.name = name;
    }
}
