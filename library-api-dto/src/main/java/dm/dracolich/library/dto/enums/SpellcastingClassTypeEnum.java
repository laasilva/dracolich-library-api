package dm.dracolich.library.dto.enums;

public enum SpellcastingClassTypeEnum {
    ARTIFICER("ARTIFICER", "HALF"),
    BARD("BARD", "FULL"),
    CLERIC("CLERIC", "FULL"),
    DRUID("DRUID", "FULL"),
    OTHER("OTHER", "OTHER"),
    RANGER("RANGER", "HALF"),
    SORCERER("SORCERER", "FULL"),
    PALADIN("PALADIN", "HALF"),
    WARLOCK("WARLOCK", "PACT"),
    WIZARD("WIZARD", "FULL");

    private String name;
    private String type;

    SpellcastingClassTypeEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
