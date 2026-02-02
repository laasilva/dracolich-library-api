package dm.dracolich.library.web.config.initializer;

import dm.dracolich.library.dto.enums.SizeEnum;
import dm.dracolich.library.web.entity.RaceEntity;
import dm.dracolich.library.web.entity.SpellEntity;
import dm.dracolich.library.web.entity.SubraceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static dm.dracolich.library.dto.enums.AbilityTypeEnum.*;

@Component
public class RaceInitializer {
    private Map<String, SpellEntity> spellCache;

    // Set the spell cache before creating races/subraces
    public void setSpellCache(Map<String, SpellEntity> spellCache) {
        this.spellCache = spellCache;
    }

    // Helper method to safely get a spell by name from the cache
    private SpellEntity getSpell(String name) {
        return Objects.requireNonNull(spellCache.get(name),
                "Spell not found in cache: " + name);
    }

    // ==================== RACE CREATION ====================
    public List<RaceEntity> createRaces() {
        return List.of(
                createAasimar(),
                createDragonborn(),
                createDwarf(),
                createElf(),
                createGnome(),
                createGoliath(),
                createHalfling(),
                createHuman(),
                createOrc(),
                createTiefling(),
                // Additional races
                createHalfElf(),
                createHalfOrc(),
                createTabaxi(),
                createFirbolg(),
                createKenku(),
                createLizardfolk(),
                createTriton(),
                createGoblin(),
                createBugbear(),
                createHobgoblin(),
                createKobold(),
                createYuanTi(),
                createGithyanki(),
                createGithzerai()
        );
    }

    // ==================== SUBRACE CREATION ====================
    public List<SubraceEntity> createSubraces(Map<String, String> raceNameMap) {
        List<SubraceEntity> subraces = new ArrayList<>();

        // ===== AASIMAR SUBRACES (Celestial Revelation options) =====
        subraces.add(SubraceEntity.builder()
                .name("Heavenly Wings Aasimar")
                .description("You can use a Bonus Action to unleash the celestial energy within yourself, transforming for 1 minute. Spectral wings appear on your back that last for the duration. You can fly at a speed equal to your Speed.")
                .raceName(raceNameMap.get("Aasimar"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Inner Radiance Aasimar")
                .description("You can use a Bonus Action to unleash the celestial energy within yourself, transforming for 1 minute. Searing light temporarily radiates from your eyes and mouth. For the duration, you shed Bright Light in a 10-foot radius and Dim Light for an additional 10 feet. At the end of each of your turns, each creature within 10 feet takes Radiant damage equal to your Proficiency Bonus.")
                .raceName(raceNameMap.get("Aasimar"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Necrotic Shroud Aasimar")
                .description("You can use a Bonus Action to unleash the celestial energy within yourself, transforming for 1 minute. Your eyes briefly become pools of darkness, and ghostly, flightless wings sprout from your back temporarily. Creatures other than your allies within 10 feet must succeed on a Charisma saving throw or have the Frightened condition.")
                .raceName(raceNameMap.get("Aasimar"))
                .build());

        // ===== DRAGONBORN SUBRACES (Dragon Ancestry) =====
        String[] dragonAncestries = {
                "Black Dragonborn", "Acid", "5 by 30 ft. line", "Dexterity",
                "Blue Dragonborn", "Lightning", "5 by 30 ft. line", "Dexterity",
                "Brass Dragonborn", "Fire", "5 by 30 ft. line", "Dexterity",
                "Bronze Dragonborn", "Lightning", "5 by 30 ft. line", "Dexterity",
                "Copper Dragonborn", "Acid", "5 by 30 ft. line", "Dexterity",
                "Gold Dragonborn", "Fire", "15 ft. cone", "Dexterity",
                "Green Dragonborn", "Poison", "15 ft. cone", "Constitution",
                "Red Dragonborn", "Fire", "15 ft. cone", "Dexterity",
                "Silver Dragonborn", "Cold", "15 ft. cone", "Constitution",
                "White Dragonborn", "Cold", "15 ft. cone", "Constitution"
        };
        for (int i = 0; i < dragonAncestries.length; i += 4) {
            subraces.add(SubraceEntity.builder()
                    .name(dragonAncestries[i])
                    .description(String.format("%s have %s breath and resistance to %s damage. Their breath weapon is a %s (%s save).",
                            dragonAncestries[i], dragonAncestries[i + 1].toLowerCase(), dragonAncestries[i + 1].toLowerCase(),
                            dragonAncestries[i + 2], dragonAncestries[i + 3]))
                    .raceName(raceNameMap.get("Dragonborn"))
                    .build());
        }

        // ===== DWARF SUBRACES =====
        subraces.add(SubraceEntity.builder()
                .name("Hill Dwarf")
                .description("As a hill dwarf, you have keen senses, deep intuition, and remarkable resilience. You gain +1 Wisdom and your hit point maximum increases by 1, and it increases by 1 every time you gain a level.")
                .raceName(raceNameMap.get("Dwarf"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Mountain Dwarf")
                .description("As a mountain dwarf, you're strong and hardy, accustomed to a difficult life in rugged terrain. You gain +2 Strength and proficiency with light and medium armor.")
                .raceName(raceNameMap.get("Dwarf"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Duergar")
                .description("Duergar, also known as gray dwarves, are a subterranean race. You gain +1 Strength, Superior Darkvision (120 ft.), and innate spellcasting.")
                .raceName(raceNameMap.get("Dwarf"))
                .cantripsAndSpells(Map.of(
                        3, List.of(getSpell("Enlarge/Reduce")),
                        5, List.of(getSpell("Invisibility"))))
                .build());

        // ===== ELF SUBRACES (Elven Lineage) =====
        subraces.add(SubraceEntity.builder()
                .name("Drow")
                .description("The drow lineage grants Superior Darkvision (120 ft.) and innate spellcasting. You know the Dancing Lights cantrip, gain Faerie Fire at 3rd level, and Darkness at 5th level.")
                .raceName(raceNameMap.get("Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Dancing Lights")),
                        3, List.of(getSpell("Faerie Fire")),
                        5, List.of(getSpell("Darkness"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("High Elf")
                .description("The high elf lineage grants you a wizard cantrip of your choice using Intelligence. At 3rd level you learn Detect Magic, and at 5th level Misty Step.")
                .raceName(raceNameMap.get("Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Prestidigitation")),
                        3, List.of(getSpell("Detect Magic")),
                        5, List.of(getSpell("Misty Step"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Wood Elf")
                .description("The wood elf lineage grants you increased speed (35 ft.), the ability to hide in natural phenomena, and innate spellcasting. You know Druidcraft, gain Longstrider at 3rd level, and Pass without Trace at 5th level.")
                .raceName(raceNameMap.get("Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Druidcraft")),
                        3, List.of(getSpell("Longstrider")),
                        5, List.of(getSpell("Pass Without Trace"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Eladrin")
                .description("Eladrin are elves native to the Feywild. You gain the ability to Fey Step (teleport 30 feet) as a bonus action, with an additional effect based on your current season.")
                .raceName(raceNameMap.get("Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Misty Step"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Sea Elf")
                .description("Sea elves fell in love with the wild beauty of the ocean. You gain a swim speed equal to your walking speed, can breathe underwater, and can communicate simple ideas with beasts that breathe water.")
                .raceName(raceNameMap.get("Elf"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Shadar-kai")
                .description("Sworn to the Raven Queen's service, shadar-kai have resistance to necrotic damage and can teleport as a bonus action, gaining resistance to all damage until the start of your next turn.")
                .raceName(raceNameMap.get("Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Misty Step"))))
                .build());

        // ===== GNOME SUBRACES (Gnomish Lineage) =====
        subraces.add(SubraceEntity.builder()
                .name("Forest Gnome")
                .description("Forest gnomes have a natural knack for illusion and can communicate with small beasts. You know the Minor Illusion cantrip, can cast Speak with Animals, and gain additional spells at higher levels.")
                .raceName(raceNameMap.get("Gnome"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Minor Illusion"), getSpell("Speak with Animals")),
                        3, List.of(getSpell("Animal Friendship")),
                        5, List.of(getSpell("Speak with Animals"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Rock Gnome")
                .description("Rock gnomes have a natural inventiveness and can create clockwork devices. You gain proficiency with tinker's tools and can create Tiny clockwork devices.")
                .raceName(raceNameMap.get("Gnome"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Mending"), getSpell("Prestidigitation")),
                        3, List.of(getSpell("Unseen Servant"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Deep Gnome")
                .description("Deep gnomes (svirfneblin) have Superior Darkvision (120 ft.) and Stone Camouflage. They gain innate spellcasting related to earth and concealment.")
                .raceName(raceNameMap.get("Gnome"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Disguise Self")),
                        5, List.of(getSpell("Nondetection"))))
                .build());

        // ===== GOLIATH SUBRACES (Giant Ancestry) =====
        subraces.add(SubraceEntity.builder()
                .name("Cloud Giant Goliath")
                .description("You have Cloud's Jaunt: As a Bonus Action, you magically teleport up to 30 feet to an unoccupied space you can see.")
                .raceName(raceNameMap.get("Goliath"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Misty Step"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Fire Giant Goliath")
                .description("You have Fire's Burn: When you hit a target with an attack roll and deal damage, you can deal extra 1d10 Fire damage.")
                .raceName(raceNameMap.get("Goliath"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Frost Giant Goliath")
                .description("You have Frost's Chill: When you hit a target with an attack roll and deal damage, you can deal extra 1d6 Cold damage and reduce its speed by 10 feet until the start of your next turn.")
                .raceName(raceNameMap.get("Goliath"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Hill Giant Goliath")
                .description("You have Hill's Tumble: When you hit a Large or smaller creature with an attack roll, you can knock that target Prone.")
                .raceName(raceNameMap.get("Goliath"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Stone Giant Goliath")
                .description("You have Stone's Endurance: When you take damage, you can use your Reaction to roll 1d12 + Constitution modifier and reduce the damage by that total.")
                .raceName(raceNameMap.get("Goliath"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Storm Giant Goliath")
                .description("You have Storm's Thunder: When you take damage, you can deal 1d8 Thunder damage to any creature within 60 feet that you can see.")
                .raceName(raceNameMap.get("Goliath"))
                .build());

        // ===== HALFLING SUBRACES =====
        subraces.add(SubraceEntity.builder()
                .name("Lightfoot Halfling")
                .description("You can attempt to hide even when obscured only by a creature at least one size larger than you. Naturally Stealthy makes you excellent scouts and rogues.")
                .raceName(raceNameMap.get("Halfling"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Stout Halfling")
                .description("You have advantage on saving throws against poison, and you have resistance to poison damage. Stout Resilience makes you hardy adventurers.")
                .raceName(raceNameMap.get("Halfling"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Ghostwise Halfling")
                .description("You can speak telepathically to any creature within 30 feet of you. The creature understands you only if you share a language. Silent Speech makes you mysterious communicators.")
                .raceName(raceNameMap.get("Halfling"))
                .build());

        // ===== HUMAN SUBRACES =====
        subraces.add(SubraceEntity.builder()
                .name("Variant Human")
                .description("Instead of +1 to all abilities, you gain +1 to two different ability scores, proficiency in one skill of your choice, and one feat of your choice.")
                .raceName(raceNameMap.get("Human"))
                .build());

        // ===== TIEFLING SUBRACES (Fiendish Legacy) =====
        subraces.add(SubraceEntity.builder()
                .name("Abyssal Tiefling")
                .description("Abyssal tieflings are linked to the chaotic demons of the Abyss. You know the Poison Spray cantrip, gain Ray of Sickness at 3rd level, and Hold Person at 5th level.")
                .raceName(raceNameMap.get("Tiefling"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Poison Spray")),
                        3, List.of(getSpell("Ray of Sickness")),
                        5, List.of(getSpell("Hold Person"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Chthonic Tiefling")
                .description("Chthonic tieflings are linked to the underworld. You know the Chill Touch cantrip, gain False Life at 3rd level, and Ray of Enfeeblement at 5th level.")
                .raceName(raceNameMap.get("Tiefling"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Chill Touch")),
                        3, List.of(getSpell("False Life")),
                        5, List.of(getSpell("Ray of Enfeeblement"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Infernal Tiefling")
                .description("Infernal tieflings are linked to the Nine Hells. You know the Fire Bolt cantrip, gain Hellish Rebuke at 3rd level, and Darkness at 5th level.")
                .raceName(raceNameMap.get("Tiefling"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Fire Bolt")),
                        3, List.of(getSpell("Hellish Rebuke")),
                        5, List.of(getSpell("Darkness"))))
                .build());

        // ===== HALF-ELF SUBRACES =====
        subraces.add(SubraceEntity.builder()
                .name("Half-Elf (High Elf Heritage)")
                .description("You gain one wizard cantrip of your choice (using Intelligence), representing the high elf magic in your blood.")
                .raceName(raceNameMap.get("Half-Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Prestidigitation"))))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Half-Elf (Wood Elf Heritage)")
                .description("Your base walking speed increases to 35 feet, and you can attempt to hide even when you are only lightly obscured by foliage, heavy rain, falling snow, mist, and other natural phenomena.")
                .raceName(raceNameMap.get("Half-Elf"))
                .build());
        subraces.add(SubraceEntity.builder()
                .name("Half-Elf (Drow Heritage)")
                .description("You know the Dancing Lights cantrip and have Superior Darkvision (60 ft.). At 3rd level, you can cast Faerie Fire once per long rest.")
                .raceName(raceNameMap.get("Half-Elf"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Dancing Lights")),
                        3, List.of(getSpell("Faerie Fire"))))
                .build());

        // ===== TRITON SUBRACES =====
        subraces.add(SubraceEntity.builder()
                .name("Triton")
                .description("Tritons can breathe air and water, have a swim speed of 30 feet, resistance to cold damage, and innate spellcasting related to water and storms.")
                .raceName(raceNameMap.get("Triton"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Fog Cloud")),
                        3, List.of(getSpell("Gust of Wind")),
                        5, List.of(getSpell("Wall of Water"))))
                .build());

        // ===== FIRBOLG =====
        subraces.add(SubraceEntity.builder()
                .name("Firbolg")
                .description("Firbolgs can use Hidden Step to turn invisible and have innate spellcasting tied to nature. They can detect and speak with beasts and plants.")
                .raceName(raceNameMap.get("Firbolg"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Detect Magic"), getSpell("Disguise Self"))))
                .build());

        // ===== GITHYANKI =====
        subraces.add(SubraceEntity.builder()
                .name("Githyanki")
                .description("Githyanki are fierce warriors who escaped Mind Flayer slavery. They have innate psionic abilities and martial training.")
                .raceName(raceNameMap.get("Githyanki"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Mage Hand")),
                        3, List.of(getSpell("Jump")),
                        5, List.of(getSpell("Misty Step"))))
                .build());

        // ===== GITHZERAI =====
        subraces.add(SubraceEntity.builder()
                .name("Githzerai")
                .description("Githzerai are monks and philosophers who dwell in Limbo. They have strong psionic abilities focused on defense and mobility.")
                .raceName(raceNameMap.get("Githzerai"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Mage Hand")),
                        3, List.of(getSpell("Shield")),
                        5, List.of(getSpell("Detect Thoughts"))))
                .build());

        // ===== YUAN-TI =====
        subraces.add(SubraceEntity.builder()
                .name("Yuan-ti Pureblood")
                .description("Yuan-ti have snake-like features and innate magic. You have Magic Resistance, Poison Immunity, and innate spellcasting.")
                .raceName(raceNameMap.get("Yuan-Ti"))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Poison Spray")),
                        3, List.of(getSpell("Animal Friendship")),
                        5, List.of(getSpell("Suggestion"))))
                .build());

        return subraces;
    }

    // ==================== AASIMAR ====================
    private RaceEntity createAasimar() {
        return RaceEntity.builder()
                .name("Aasimar")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CHARISMA, 2, WISDOM, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Light")),
                        3, List.of(getSpell("Lesser Restoration"))))
                .custom(false)
                .build();
    }

    // ==================== DRAGONBORN ====================
    private RaceEntity createDragonborn() {
        return RaceEntity.builder()
                .name("Dragonborn")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 2, CHARISMA, 1))
                .custom(false)
                .build();
    }

    // ==================== DWARF ====================
    private RaceEntity createDwarf() {
        return RaceEntity.builder()
                .name("Dwarf")
                .size(SizeEnum.MEDIUM)
                .speed(25)
                .abilityBonus(Map.of(CONSTITUTION, 2))
                .custom(false)
                .build();
    }

    // ==================== ELF ====================
    private RaceEntity createElf() {

        return RaceEntity.builder()
                .name("Elf")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .cantripsAndSpells(Map.of(1, List.of(getSpell("Darkvision"))))
                .abilityBonus(Map.of(DEXTERITY, 2))
                .custom(false)
                .build();
    }

    // ==================== GNOME ====================
    private RaceEntity createGnome() {
        return RaceEntity.builder()
                .name("Gnome")
                .size(SizeEnum.SMALL)
                .speed(25)
                .abilityBonus(Map.of(INTELLIGENCE, 2))
                .custom(false)
                .build();
    }

    // ==================== GOLIATH ====================
    private RaceEntity createGoliath() {
        return RaceEntity.builder()
                .name("Goliath")
                .size(SizeEnum.MEDIUM)
                .speed(35)
                .abilityBonus(Map.of(STRENGTH, 2, CONSTITUTION, 1))
                .custom(false)
                .build();
    }

    // ==================== HALFLING ====================
    private RaceEntity createHalfling() {
        return RaceEntity.builder()
                .name("Halfling")
                .size(SizeEnum.SMALL)
                .speed(25)
                .abilityBonus(Map.of(DEXTERITY, 2))
                .custom(false)
                .build();
    }

    // ==================== HUMAN ====================
    private RaceEntity createHuman() {
        return RaceEntity.builder()
                .name("Human")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(
                        STRENGTH, 1,
                        DEXTERITY, 1,
                        CONSTITUTION, 1,
                        INTELLIGENCE, 1,
                        WISDOM, 1,
                        CHARISMA, 1
                ))
                .custom(false)
                .build();
    }

    // ==================== ORC ====================
    private RaceEntity createOrc() {
        return RaceEntity.builder()
                .name("Orc")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 2, CONSTITUTION, 1))
                .custom(false)
                .build();
    }

    // ==================== TIEFLING ====================
    private RaceEntity createTiefling() {
        return RaceEntity.builder()
                .name("Tiefling")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CHARISMA, 2, INTELLIGENCE, 1))
                .custom(false)
                .build();
    }

    // ==================== HALF-ELF ====================
    private RaceEntity createHalfElf() {
        return RaceEntity.builder()
                .name("Half-Elf")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CHARISMA, 2)) // +1 to two other abilities of choice
                .custom(false)
                .build();
    }

    // ==================== HALF-ORC ====================
    private RaceEntity createHalfOrc() {
        return RaceEntity.builder()
                .name("Half-Orc")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 2, CONSTITUTION, 1))
                .custom(false)
                .build();
    }

    // ==================== TABAXI ====================
    private RaceEntity createTabaxi() {
        return RaceEntity.builder()
                .name("Tabaxi")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(DEXTERITY, 2, CHARISMA, 1))
                .custom(false)
                .build();
    }

    // ==================== FIRBOLG ====================
    private RaceEntity createFirbolg() {
        return RaceEntity.builder()
                .name("Firbolg")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(WISDOM, 2, STRENGTH, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Detect Magic"), getSpell("Disguise Self"))))
                .custom(false)
                .build();
    }

    // ==================== KENKU ====================
    private RaceEntity createKenku() {
        return RaceEntity.builder()
                .name("Kenku")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(DEXTERITY, 2, WISDOM, 1))
                .custom(false)
                .build();
    }

    // ==================== LIZARDFOLK ====================
    private RaceEntity createLizardfolk() {
        return RaceEntity.builder()
                .name("Lizardfolk")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CONSTITUTION, 2, WISDOM, 1))
                .custom(false)
                .build();
    }

    // ==================== TRITON ====================
    private RaceEntity createTriton() {
        return RaceEntity.builder()
                .name("Triton")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 1, CONSTITUTION, 1, CHARISMA, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Fog Cloud")),
                        3, List.of(getSpell("Gust of Wind")),
                        5, List.of(getSpell("Wall of Water"))))
                .custom(false)
                .build();
    }

    // ==================== GOBLIN ====================
    private RaceEntity createGoblin() {
        return RaceEntity.builder()
                .name("Goblin")
                .size(SizeEnum.SMALL)
                .speed(30)
                .abilityBonus(Map.of(DEXTERITY, 2, CONSTITUTION, 1))
                .custom(false)
                .build();
    }

    // ==================== BUGBEAR ====================
    private RaceEntity createBugbear() {
        return RaceEntity.builder()
                .name("Bugbear")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 2, DEXTERITY, 1))
                .custom(false)
                .build();
    }

    // ==================== HOBGOBLIN ====================
    private RaceEntity createHobgoblin() {
        return RaceEntity.builder()
                .name("Hobgoblin")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CONSTITUTION, 2, INTELLIGENCE, 1))
                .custom(false)
                .build();
    }

    // ==================== KOBOLD ====================
    private RaceEntity createKobold() {
        return RaceEntity.builder()
                .name("Kobold")
                .size(SizeEnum.SMALL)
                .speed(30)
                .abilityBonus(Map.of(DEXTERITY, 2))
                .custom(false)
                .build();
    }

    // ==================== YUAN-TI ====================
    private RaceEntity createYuanTi() {
        return RaceEntity.builder()
                .name("Yuan-Ti")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(CHARISMA, 2, INTELLIGENCE, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Poison Spray")),
                        3, List.of(getSpell("Animal Friendship")),
                        5, List.of(getSpell("Suggestion"))))
                .custom(false)
                .build();
    }

    // ==================== GITHYANKI ====================
    private RaceEntity createGithyanki() {
        return RaceEntity.builder()
                .name("Githyanki")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(STRENGTH, 2, INTELLIGENCE, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Mage Hand")),
                        3, List.of(getSpell("Jump")),
                        5, List.of(getSpell("Misty Step"))))
                .custom(false)
                .build();
    }

    // ==================== GITHZERAI ====================
    private RaceEntity createGithzerai() {
        return RaceEntity.builder()
                .name("Githzerai")
                .size(SizeEnum.MEDIUM)
                .speed(30)
                .abilityBonus(Map.of(WISDOM, 2, INTELLIGENCE, 1))
                .cantripsAndSpells(Map.of(
                        1, List.of(getSpell("Mage Hand")),
                        3, List.of(getSpell("Shield")),
                        5, List.of(getSpell("Detect Thoughts"))))
                .custom(false)
                .build();
    }
}
