package dm.dracolich.library.web.config.initializer;

import dm.dracolich.library.dto.ClassLevelProgressionDto;
import dm.dracolich.library.dto.StartingEquipmentItemDto;
import dm.dracolich.library.dto.enums.DiceTypeEnum;
import dm.dracolich.library.web.entity.ClassEntity;
import dm.dracolich.library.web.entity.FeatureEntity;
import org.springframework.stereotype.Component;

import java.util.*;

import static dm.dracolich.library.dto.enums.SkillsEnum.*;
import static dm.dracolich.library.dto.enums.AbilityTypeEnum.*;
import static dm.dracolich.library.dto.enums.ArmorTypeEnum.*;
import static dm.dracolich.library.dto.enums.WeaponTypeEnum.*;

@Component
public class ClassInitializer {

    // ==================== CLASS CREATION ====================
    public List<ClassEntity> createClasses() {
        return List.of(
                createBarbarian(),
                createBard(),
                createCleric(),
                createDruid(),
                createFighter(),
                createMonk(),
                createPaladin(),
                createRanger(),
                createRogue(),
                createSorcerer(),
                createWarlock(),
                createWizard()
        );
    }

    // ==================== BARBARIAN ====================
    private ClassEntity createBarbarian() {
        return ClassEntity.builder()
                .name("barbarian")
                .description("Barbarians are powerful warriors whose strength comes from their rage. Able to use their anger as a weapon. The subclass represents different paths they walk, which teaches them special ways to use their anger.")
                .skillProficiencies(Map.of(2, Set.of(ANIMAL_HANDLING, ATHLETICS, INTIMIDATION, NATURE, PERCEPTION, SURVIVAL)))
                .hitDice(DiceTypeEnum.D12)
                .savingThrows(Set.of(STRENGTH, CONSTITUTION))
                .armorTraining(Set.of(LIGHT, MEDIUM, SHIELDS))
                .weaponTraining(Set.of(SIMPLE, MARTIAL))
                .startingEquipmentA(Set.of(
                        item("Greataxe", 1),
                        item("Handaxe", 2),
                        item("Explorer's Pack", 1),
                        item("Javelin", 4),
                        item("GP", 15)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 75)
                ))
                .levelProgression(getBarbarianProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getBarbarianProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Rage", "Unarmored Defense"))
                        .scalingValues(Map.of("rages", 2, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Reckless Attack", "Danger Sense"))
                        .scalingValues(Map.of("rages", 2, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Primal Path"))
                        .scalingValues(Map.of("rages", 3, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("rages", 3, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Extra Attack", "Fast Movement"))
                        .scalingValues(Map.of("rages", 3, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Path Feature"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Feral Instinct"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 2)).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of("Brutal Critical"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 3, "brutalCriticalDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Path Feature"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 3, "brutalCriticalDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Relentless Rage"))
                        .scalingValues(Map.of("rages", 4, "rageDamage", 3, "brutalCriticalDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("rages", 5, "rageDamage", 3, "brutalCriticalDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Brutal Critical"))
                        .scalingValues(Map.of("rages", 5, "rageDamage", 3, "brutalCriticalDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Path Feature"))
                        .scalingValues(Map.of("rages", 5, "rageDamage", 3, "brutalCriticalDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Persistent Rage"))
                        .scalingValues(Map.of("rages", 5, "rageDamage", 3, "brutalCriticalDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("rages", 5, "rageDamage", 4, "brutalCriticalDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Brutal Critical"))
                        .scalingValues(Map.of("rages", 6, "rageDamage", 4, "brutalCriticalDice", 3)).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Indomitable Might"))
                        .scalingValues(Map.of("rages", 6, "rageDamage", 4, "brutalCriticalDice", 3)).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("rages", 6, "rageDamage", 4, "brutalCriticalDice", 3)).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Primal Champion"))
                        .scalingValues(Map.of("rages", "Unlimited", "rageDamage", 4, "brutalCriticalDice", 3)).build()
        );
    }

    // ==================== BARD ====================
    private ClassEntity createBard() {
        return ClassEntity.builder()
                .name("bard")
                .description("A poet, a singer, a storyteller. There are those that seek to bring wonder to the world, and their magic comes from their emotions from the stories they tell and the songs they sing. Bards have different colleges that they follow to hone their skills in magic.")
                .skillProficiencies(Map.of(3, Set.of(ACROBATICS, ANIMAL_HANDLING, ARCANA, ATHLETICS, DECEPTION, HISTORY, INSIGHT, INTIMIDATION, INVESTIGATION, MEDICINE, NATURE, PERCEPTION, PERFORMANCE, PERSUASION, RELIGION, SLEIGHT_OF_HAND, STEALTH, SURVIVAL)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(DEXTERITY, CHARISMA))
                .weaponTraining(Set.of(SIMPLE))
                .armorTraining(Set.of(LIGHT))
                .startingEquipmentA(Set.of(
                        item("Rapier", 1),
                        item("Diplomat's Pack", 1),
                        item("Lute", 1),
                        item("Leather Armor", 1),
                        item("Dagger", 1),
                        item("GP", 19)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 90)
                ))
                .levelProgression(getBardProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getBardProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Spellcasting", "Bardic Inspiration"))
                        .scalingValues(Map.of("bardicDie", "d6", "cantrips", 2, "spellsKnown", 4, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Jack of All Trades", "Song of Rest"))
                        .scalingValues(Map.of("bardicDie", "d6", "cantrips", 2, "spellsKnown", 5, "songOfRestDie", "d6", "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Bard College", "Expertise"))
                        .scalingValues(Map.of("bardicDie", "d6", "cantrips", 2, "spellsKnown", 6, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("bardicDie", "d6", "cantrips", 3, "spellsKnown", 7, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Bardic Inspiration", "Font of Inspiration"))
                        .scalingValues(Map.of("bardicDie", "d8", "cantrips", 3, "spellsKnown", 8, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Countercharm", "Bard College Feature"))
                        .scalingValues(Map.of("bardicDie", "d8", "cantrips", 3, "spellsKnown", 9, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("bardicDie", "d8", "cantrips", 3, "spellsKnown", 10, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("bardicDie", "d8", "cantrips", 3, "spellsKnown", 11, "songOfRestDie", "d6", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of("Song of Rest"))
                        .scalingValues(Map.of("bardicDie", "d8", "cantrips", 3, "spellsKnown", 12, "songOfRestDie", "d8", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Bardic Inspiration", "Expertise", "Magical Secrets"))
                        .scalingValues(Map.of("bardicDie", "d10", "cantrips", 4, "spellsKnown", 14, "songOfRestDie", "d8", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of())
                        .scalingValues(Map.of("bardicDie", "d10", "cantrips", 4, "spellsKnown", 15, "songOfRestDie", "d8", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("bardicDie", "d10", "cantrips", 4, "spellsKnown", 15, "songOfRestDie", "d8", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Song of Rest"))
                        .scalingValues(Map.of("bardicDie", "d10", "cantrips", 4, "spellsKnown", 16, "songOfRestDie", "d10", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Magical Secrets", "Bard College Feature"))
                        .scalingValues(Map.of("bardicDie", "d10", "cantrips", 4, "spellsKnown", 18, "songOfRestDie", "d10", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Bardic Inspiration"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 19, "songOfRestDie", "d10", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 19, "songOfRestDie", "d10", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Song of Rest"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 20, "songOfRestDie", "d12", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Magical Secrets"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 22, "songOfRestDie", "d12", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 22, "songOfRestDie", "d12", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Superior Inspiration"))
                        .scalingValues(Map.of("bardicDie", "d12", "cantrips", 4, "spellsKnown", 22, "songOfRestDie", "d12", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 2, 8, 1, 9, 1))).build()
        );
    }

    // ==================== CLERIC ====================
    private ClassEntity createCleric() {
        return ClassEntity.builder()
                .name("cleric")
                .description("Ideas are not just a simple blueprint for a machine or an urge to buy new farming equipment. Ideas are immortal things that can topple empires and bring justice to those that threaten others. Ideas are some of the most powerful forces in the universe. All it needs is a champion, and clerics are those champions. Usually following a pantheon or a god/goddess, they devote their lives to serving that idea.")
                .skillProficiencies(Map.of(2, Set.of(HISTORY, INSIGHT, MEDICINE, PERSUASION, RELIGION)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(WISDOM, CHARISMA))
                .armorTraining(Set.of(LIGHT, MEDIUM, SHIELDS))
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Mace", 1),
                        item("Scale Mail", 1),
                        item("Light Crossbow", 1),
                        item("Crossbow Bolts (20)", 1),
                        item("Priest's Pack", 1),
                        item("Shield", 1),
                        item("Holy Symbol (Amulet)", 1),
                        item("GP", 7)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 110)
                ))
                .levelProgression(getClericProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getClericProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Spellcasting", "Divine Domain"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Channel Divinity", "Divine Domain Feature"))
                        .scalingValues(Map.of("cantrips", 3, "channelDivinityUses", 1, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 3, "channelDivinityUses", 1, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 1, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Destroy Undead"))
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 1, "destroyUndeadCR", "1/2", "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Channel Divinity", "Divine Domain Feature"))
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 2, "destroyUndeadCR", "1/2", "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 2, "destroyUndeadCR", "1/2", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement", "Destroy Undead"))
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 2, "destroyUndeadCR", "1", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "channelDivinityUses", 2, "destroyUndeadCR", "1", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Divine Intervention"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "1", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Destroy Undead"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "2", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "2", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "2", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Destroy Undead"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "3", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "3", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "3", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Destroy Undead", "Divine Domain Feature"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 2, "destroyUndeadCR", "4", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Channel Divinity"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 3, "destroyUndeadCR", "4", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 3, "destroyUndeadCR", "4", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Divine Intervention Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "channelDivinityUses", 3, "destroyUndeadCR", "4", "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 2, 8, 1, 9, 1))).build()
        );
    }

    // ==================== DRUID ====================
    private ClassEntity createDruid() {
        return ClassEntity.builder()
                .name("druid")
                .description("Druids are protectors of nature. They are the embodiment of it's wrath and it's beauty. They have the ability to shapeshift into creatures and special skills, based on which circle they follow which is a further representation of different parts of nature.")
                .skillProficiencies(Map.of(2, Set.of(ARCANA, ANIMAL_HANDLING, INSIGHT, MEDICINE, NATURE, PERCEPTION, RELIGION, SURVIVAL)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(INTELLIGENCE, WISDOM))
                .armorTraining(Set.of(LIGHT, MEDIUM, SHIELDS))
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Wooden Shield", 1),
                        item("Scimitar", 1),
                        item("Leather Armor", 1),
                        item("Explorer's Pack", 1),
                        item("Druidic Focus (Totem)", 1),
                        item("GP", 9)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 50)
                ))
                .levelProgression(getDruidProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getDruidProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Druidic", "Spellcasting"))
                        .scalingValues(Map.of("cantrips", 2, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Wild Shape", "Druid Circle"))
                        .scalingValues(Map.of("cantrips", 2, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 2, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Wild Shape Improvement", "Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Druid Circle Feature"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Wild Shape Improvement", "Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Druid Circle Feature"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Druid Circle Feature"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Timeless Body", "Beast Spells"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Archdruid"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 2, 8, 1, 9, 1))).build()
        );
    }

    // ==================== FIGHTER ====================
    private ClassEntity createFighter() {
        return ClassEntity.builder()
                .name("fighter")
                .description("Swordsman ship, spears, shields, bows and crossbows. All these bring about images of fighters. Warriors who hone their combative ability to a deadly skill that rivals none. Some use magic to enhance their ability, while others crush magic in favor of pure muscle or acrobatic skills.")
                .skillProficiencies(Map.of(2, Set.of(ACROBATICS, ANIMAL_HANDLING, ATHLETICS, HISTORY, INSIGHT, INTIMIDATION, PERCEPTION, SURVIVAL)))
                .hitDice(DiceTypeEnum.D10)
                .savingThrows(Set.of(STRENGTH, CONSTITUTION))
                .armorTraining(Set.of(LIGHT, MEDIUM, HEAVY, SHIELDS))
                .weaponTraining(Set.of(SIMPLE, MARTIAL))
                .startingEquipmentA(Set.of(
                        item("Chain Mail", 1),
                        item("Greatsword", 1),
                        item("Flail", 1),
                        item("Javelin", 8),
                        item("Dungeoneer's Pack", 1),
                        item("GP", 4)
                ))
                .startingEquipmentB(Set.of(
                        item("Studded Leather Armor", 1),
                        item("Scimitar", 1),
                        item("Shortsword", 1),
                        item("Longbow", 1),
                        item("Arrow", 20),
                        item("Quiver", 1),
                        item("Dungeoneer's Pack", 1),
                        item("GP", 11)
                ))
                .startingEquipmentC(Set.of(
                        item("GP", 155)
                ))
                .levelProgression(getFighterProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getFighterProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Fighting Style", "Second Wind"))
                        .scalingValues(Map.of("actionSurgeUses", 0, "indomitableUses", 0, "extraAttacks", 1)).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Action Surge"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 1)).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Martial Archetype"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 1)).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 1)).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Extra Attack"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Martial Archetype Feature"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 0, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of("Indomitable"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 1, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Martial Archetype Feature"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 1, "extraAttacks", 2)).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Extra Attack"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 1, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 1, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Indomitable"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 2, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 2, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Martial Archetype Feature"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 2, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 1, "indomitableUses", 2, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Action Surge", "Indomitable"))
                        .scalingValues(Map.of("actionSurgeUses", 2, "indomitableUses", 3, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Martial Archetype Feature"))
                        .scalingValues(Map.of("actionSurgeUses", 2, "indomitableUses", 3, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("actionSurgeUses", 2, "indomitableUses", 3, "extraAttacks", 3)).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Extra Attack"))
                        .scalingValues(Map.of("actionSurgeUses", 2, "indomitableUses", 3, "extraAttacks", 4)).build()
        );
    }

    // ==================== MONK ====================
    private ClassEntity createMonk() {
        return ClassEntity.builder()
                .name("monk")
                .description("Kung Fu, Karate, Drunken Fighting, Dr. Strange, ect. All of these are what the D&D Monks train in. Whether it's using your fist, spirit, ki or weapons that are nothing but an extension of your will. Monks can deliver a storm of powerful melee attacks.")
                .skillProficiencies(Map.of(2, Set.of(ACROBATICS, ATHLETICS, HISTORY, INSIGHT, RELIGION, STEALTH)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(STRENGTH, DEXTERITY))
                .armorTraining(Set.of())
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Spear", 1),
                        item("Dagger", 5),
                        item("Explorer's Pack", 1),
                        item("GP", 11)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 50)
                ))
                .levelProgression(getMonkProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getMonkProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Unarmored Defense", "Martial Arts"))
                        .scalingValues(Map.of("martialArtsDie", "d4", "kiPoints", 0, "unarmoredMovement", 0)).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Ki", "Unarmored Movement"))
                        .scalingValues(Map.of("martialArtsDie", "d4", "kiPoints", 2, "unarmoredMovement", 10)).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Monastic Tradition", "Deflect Missiles"))
                        .scalingValues(Map.of("martialArtsDie", "d4", "kiPoints", 3, "unarmoredMovement", 10)).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement", "Slow Fall"))
                        .scalingValues(Map.of("martialArtsDie", "d4", "kiPoints", 4, "unarmoredMovement", 10)).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Extra Attack", "Stunning Strike"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 5, "unarmoredMovement", 10)).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Ki-Empowered Strikes", "Monastic Tradition Feature"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 6, "unarmoredMovement", 15)).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Evasion", "Stillness of Mind"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 7, "unarmoredMovement", 15)).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 8, "unarmoredMovement", 15)).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of("Unarmored Movement Improvement"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 9, "unarmoredMovement", 15)).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Purity of Body"))
                        .scalingValues(Map.of("martialArtsDie", "d6", "kiPoints", 10, "unarmoredMovement", 20)).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Monastic Tradition Feature"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 11, "unarmoredMovement", 20)).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 12, "unarmoredMovement", 20)).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Tongue of the Sun and Moon"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 13, "unarmoredMovement", 20)).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Diamond Soul"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 14, "unarmoredMovement", 25)).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Timeless Body"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 15, "unarmoredMovement", 25)).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("martialArtsDie", "d8", "kiPoints", 16, "unarmoredMovement", 25)).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Monastic Tradition Feature"))
                        .scalingValues(Map.of("martialArtsDie", "d10", "kiPoints", 17, "unarmoredMovement", 25)).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Empty Body"))
                        .scalingValues(Map.of("martialArtsDie", "d10", "kiPoints", 18, "unarmoredMovement", 30)).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("martialArtsDie", "d10", "kiPoints", 19, "unarmoredMovement", 30)).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Perfect Self"))
                        .scalingValues(Map.of("martialArtsDie", "d10", "kiPoints", 20, "unarmoredMovement", 30)).build()
        );
    }

    // ==================== PALADIN ====================
    private ClassEntity createPaladin() {
        return ClassEntity.builder()
                .name("paladin")
                .description("Paladins, much like clerics, hold up ideas. However, they serve tenants with a multitude of ideas. Some of joy, others of light and conquest. Paladin holds an Oath which is the representation of said ideas.")
                .skillProficiencies(Map.of(2, Set.of(ATHLETICS, INSIGHT, INTIMIDATION, MEDICINE, PERSUASION, RELIGION)))
                .hitDice(DiceTypeEnum.D10)
                .savingThrows(Set.of(WISDOM, CHARISMA))
                .armorTraining(Set.of(LIGHT, MEDIUM, HEAVY, SHIELDS))
                .weaponTraining(Set.of(SIMPLE, MARTIAL))
                .startingEquipmentA(Set.of(
                        item("Chain Mail", 1),
                        item("Shield", 1),
                        item("Longsword", 1),
                        item("Javelin", 6),
                        item("Priest's Pack", 1),
                        item("Holy Symbol", 1),
                        item("GP", 9)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 150)
                ))
                .levelProgression(getPaladinProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getPaladinProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Divine Sense", "Lay on Hands"))
                        .scalingValues(Map.of("spellSlots", Map.of())).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Fighting Style", "Spellcasting", "Divine Smite"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Divine Health", "Sacred Oath"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Extra Attack"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Aura of Protection"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Sacred Oath Feature"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Aura of Courage"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Improved Divine Smite"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Cleansing Touch"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Sacred Oath Feature"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of())
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Aura Improvements"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Sacred Oath Feature"))
                        .scalingValues(Map.of("spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build()
        );
    }

    // ==================== RANGER ====================
    private ClassEntity createRanger() {
        return ClassEntity.builder()
                .name("ranger")
                .description("Rangers are those that wander and live within the wild. Their skill to survive in harsh nature places is bested by none. Many rangers take on a role, usually in various ways to hunt special types of monsters.")
                .skillProficiencies(Map.of(3, Set.of(ANIMAL_HANDLING, ATHLETICS, INSIGHT, INVESTIGATION, NATURE, PERCEPTION, STEALTH, SURVIVAL)))
                .hitDice(DiceTypeEnum.D10)
                .savingThrows(Set.of(STRENGTH, DEXTERITY))
                .armorTraining(Set.of(LIGHT, MEDIUM, SHIELDS))
                .weaponTraining(Set.of(SIMPLE, MARTIAL))
                .startingEquipmentA(Set.of(
                        item("Studded Leather Armor", 1),
                        item("Scimitar", 2),
                        item("Shortsword", 1),
                        item("Longbow", 1),
                        item("Arrow", 20),
                        item("Druidic Focus (Sprig of Mistletoe)", 1),
                        item("Quiver", 1),
                        item("Explorer's Pack", 1),
                        item("GP", 7)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 150)
                ))
                .levelProgression(getRangerProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getRangerProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Favored Enemy", "Natural Explorer"))
                        .scalingValues(Map.of("spellsKnown", 0, "spellSlots", Map.of())).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Fighting Style", "Spellcasting"))
                        .scalingValues(Map.of("spellsKnown", 2, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Ranger Archetype", "Primeval Awareness"))
                        .scalingValues(Map.of("spellsKnown", 3, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellsKnown", 3, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Extra Attack"))
                        .scalingValues(Map.of("spellsKnown", 4, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Favored Enemy Improvement", "Natural Explorer Improvement"))
                        .scalingValues(Map.of("spellsKnown", 4, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Ranger Archetype Feature"))
                        .scalingValues(Map.of("spellsKnown", 5, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement", "Land's Stride"))
                        .scalingValues(Map.of("spellsKnown", 5, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("spellsKnown", 6, "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Natural Explorer Improvement", "Hide in Plain Sight"))
                        .scalingValues(Map.of("spellsKnown", 6, "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Ranger Archetype Feature"))
                        .scalingValues(Map.of("spellsKnown", 7, "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellsKnown", 7, "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("spellsKnown", 8, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Favored Enemy Improvement", "Vanish"))
                        .scalingValues(Map.of("spellsKnown", 8, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Ranger Archetype Feature"))
                        .scalingValues(Map.of("spellsKnown", 9, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellsKnown", 9, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of())
                        .scalingValues(Map.of("spellsKnown", 10, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Feral Senses"))
                        .scalingValues(Map.of("spellsKnown", 10, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("spellsKnown", 11, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Foe Slayer"))
                        .scalingValues(Map.of("spellsKnown", 11, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build()
        );
    }

    // ==================== ROGUE ====================
    private ClassEntity createRogue() {
        return ClassEntity.builder()
                .name("rogue")
                .description("Rogues are talented individuals who hone skills of the unethical or criminal. A rogue's skill in thieving, killing, etc. is rivaled by none as they become experts in their field.")
                .skillProficiencies(Map.of(4, Set.of(ACROBATICS, ATHLETICS, DECEPTION, INSIGHT, INTIMIDATION,
                        INVESTIGATION, PERCEPTION, PERFORMANCE, PERSUASION, SLEIGHT_OF_HAND, STEALTH)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(DEXTERITY, INTELLIGENCE))
                .armorTraining(Set.of(LIGHT))
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Leather Armor", 1),
                        item("Dagger", 2),
                        item("Shortsword", 1),
                        item("Shortbow", 1),
                        item("Quiver", 1),
                        item("Arrow", 20),
                        item("Burglar's Pack", 1),
                        item("Thieves' Tools", 1),
                        item("GP", 8)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 100)
                ))
                .levelProgression(getRogueProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getRogueProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Expertise", "Sneak Attack", "Thieves' Cant"))
                        .scalingValues(Map.of("sneakAttackDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Cunning Action"))
                        .scalingValues(Map.of("sneakAttackDice", 1)).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Roguish Archetype"))
                        .scalingValues(Map.of("sneakAttackDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 2)).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of("Uncanny Dodge"))
                        .scalingValues(Map.of("sneakAttackDice", 3)).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Expertise"))
                        .scalingValues(Map.of("sneakAttackDice", 3)).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of("Evasion"))
                        .scalingValues(Map.of("sneakAttackDice", 4)).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 4)).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of("Roguish Archetype Feature"))
                        .scalingValues(Map.of("sneakAttackDice", 5)).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 5)).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Reliable Talent"))
                        .scalingValues(Map.of("sneakAttackDice", 6)).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 6)).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Roguish Archetype Feature"))
                        .scalingValues(Map.of("sneakAttackDice", 7)).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Blindsense"))
                        .scalingValues(Map.of("sneakAttackDice", 7)).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Slippery Mind"))
                        .scalingValues(Map.of("sneakAttackDice", 8)).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 8)).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Roguish Archetype Feature"))
                        .scalingValues(Map.of("sneakAttackDice", 9)).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Elusive"))
                        .scalingValues(Map.of("sneakAttackDice", 9)).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sneakAttackDice", 10)).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Stroke of Luck"))
                        .scalingValues(Map.of("sneakAttackDice", 10)).build()
        );
    }

    // ==================== SORCERER ====================
    private ClassEntity createSorcerer() {
        return ClassEntity.builder()
                .name("sorcerer")
                .description("Usually born with magical energy running through their veins. Sorcerers are what people usually think of when the image of a \"spellslinger\" comes to mind. Their magical force is linked to their emotions and mind.")
                .skillProficiencies(Map.of(2, Set.of(ARCANA, DECEPTION, INSIGHT,
                        INTIMIDATION, PERSUASION, RELIGION)))
                .hitDice(DiceTypeEnum.D6)
                .savingThrows(Set.of(CONSTITUTION, CHARISMA))
                .armorTraining(Set.of())
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Spear", 1),
                        item("Dagger", 1),
                        item("Arcane Focus (Crystal)", 1),
                        item("Dungeoneer's Pack", 1),
                        item("GP", 28)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 50)
                ))
                .levelProgression(getSorcererProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getSorcererProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Spellcasting", "Sorcerous Origin"))
                        .scalingValues(Map.of("sorceryPoints", 0, "cantrips", 4, "spellsKnown", 2, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Font of Magic"))
                        .scalingValues(Map.of("sorceryPoints", 2, "cantrips", 4, "spellsKnown", 3, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Metamagic"))
                        .scalingValues(Map.of("sorceryPoints", 3, "cantrips", 4, "spellsKnown", 4, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sorceryPoints", 4, "cantrips", 5, "spellsKnown", 5, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 5, "cantrips", 5, "spellsKnown", 6, "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Sorcerous Origin Feature"))
                        .scalingValues(Map.of("sorceryPoints", 6, "cantrips", 5, "spellsKnown", 7, "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 7, "cantrips", 5, "spellsKnown", 8, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sorceryPoints", 8, "cantrips", 5, "spellsKnown", 9, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 9, "cantrips", 5, "spellsKnown", 10, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Metamagic"))
                        .scalingValues(Map.of("sorceryPoints", 10, "cantrips", 6, "spellsKnown", 11, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 11, "cantrips", 6, "spellsKnown", 12, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sorceryPoints", 12, "cantrips", 6, "spellsKnown", 12, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 13, "cantrips", 6, "spellsKnown", 13, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Sorcerous Origin Feature"))
                        .scalingValues(Map.of("sorceryPoints", 14, "cantrips", 6, "spellsKnown", 13, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of())
                        .scalingValues(Map.of("sorceryPoints", 15, "cantrips", 6, "spellsKnown", 14, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sorceryPoints", 16, "cantrips", 6, "spellsKnown", 14, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Metamagic"))
                        .scalingValues(Map.of("sorceryPoints", 17, "cantrips", 6, "spellsKnown", 15, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Sorcerous Origin Feature"))
                        .scalingValues(Map.of("sorceryPoints", 18, "cantrips", 6, "spellsKnown", 15, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("sorceryPoints", 19, "cantrips", 6, "spellsKnown", 15, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Sorcerous Restoration"))
                        .scalingValues(Map.of("sorceryPoints", 20, "cantrips", 6, "spellsKnown", 15, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 2, 8, 1, 9, 1))).build()
        );
    }

    // ==================== WARLOCK ====================
    private ClassEntity createWarlock() {
        return ClassEntity.builder()
                .name("warlock")
                .description("With the dark stereotype of being evil, warlocks are feared. Usually, these are mortals who have signed a magical contract with immortal entities where the entity will gift them magical power for the price of servitude.")
                .skillProficiencies(Map.of(2, Set.of(ARCANA, DECEPTION, HISTORY,
                        INTIMIDATION, INVESTIGATION, NATURE, RELIGION)))
                .hitDice(DiceTypeEnum.D8)
                .savingThrows(Set.of(WISDOM, CHARISMA))
                .armorTraining(Set.of(LIGHT))
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Leather Armor", 1),
                        item("Sickle", 1),
                        item("Arcane Focus (Orb)", 1),
                        item("Occult Lore", 1),
                        item("Scholar's Pack", 1),
                        item("CP", 15)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 100)
                ))
                .levelProgression(getWarlockProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getWarlockProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Otherworldly Patron", "Pact Magic"))
                        .scalingValues(Map.of("invocations", 0, "cantrips", 2, "spellsKnown", 2, "pactSlots", 1, "slotLevel", "1st")).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Eldritch Invocations"))
                        .scalingValues(Map.of("invocations", 2, "cantrips", 2, "spellsKnown", 3, "pactSlots", 2, "slotLevel", "1st")).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of("Pact Boon"))
                        .scalingValues(Map.of("invocations", 2, "cantrips", 2, "spellsKnown", 4, "pactSlots", 2, "slotLevel", "2nd")).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("invocations", 2, "cantrips", 3, "spellsKnown", 5, "pactSlots", 2, "slotLevel", "2nd")).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of())
                        .scalingValues(Map.of("invocations", 3, "cantrips", 3, "spellsKnown", 6, "pactSlots", 2, "slotLevel", "3rd")).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Otherworldly Patron Feature"))
                        .scalingValues(Map.of("invocations", 3, "cantrips", 3, "spellsKnown", 7, "pactSlots", 2, "slotLevel", "3rd")).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("invocations", 4, "cantrips", 3, "spellsKnown", 8, "pactSlots", 2, "slotLevel", "4th")).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("invocations", 4, "cantrips", 3, "spellsKnown", 9, "pactSlots", 2, "slotLevel", "4th")).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("invocations", 5, "cantrips", 3, "spellsKnown", 10, "pactSlots", 2, "slotLevel", "5th")).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Otherworldly Patron Feature"))
                        .scalingValues(Map.of("invocations", 5, "cantrips", 4, "spellsKnown", 10, "pactSlots", 2, "slotLevel", "5th")).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of("Mystic Arcanum"))
                        .scalingValues(Map.of("invocations", 5, "cantrips", 4, "spellsKnown", 11, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("invocations", 6, "cantrips", 4, "spellsKnown", 11, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of("Mystic Arcanum"))
                        .scalingValues(Map.of("invocations", 6, "cantrips", 4, "spellsKnown", 12, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Otherworldly Patron Feature"))
                        .scalingValues(Map.of("invocations", 6, "cantrips", 4, "spellsKnown", 12, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of("Mystic Arcanum"))
                        .scalingValues(Map.of("invocations", 7, "cantrips", 4, "spellsKnown", 13, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("invocations", 7, "cantrips", 4, "spellsKnown", 13, "pactSlots", 3, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of("Mystic Arcanum"))
                        .scalingValues(Map.of("invocations", 7, "cantrips", 4, "spellsKnown", 14, "pactSlots", 4, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of())
                        .scalingValues(Map.of("invocations", 8, "cantrips", 4, "spellsKnown", 14, "pactSlots", 4, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("invocations", 8, "cantrips", 4, "spellsKnown", 15, "pactSlots", 4, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Eldritch Master"))
                        .scalingValues(Map.of("invocations", 8, "cantrips", 4, "spellsKnown", 15, "pactSlots", 4, "slotLevel", "5th", "mysticArcanum", Map.of(6, 1, 7, 1, 8, 1, 9, 1))).build()
        );
    }

    // ==================== WIZARD ====================
    private ClassEntity createWizard() {
        return ClassEntity.builder()
                .name("wizard")
                .description("Wizards are considered the Apex of the Arcane and spend a lot of their life in education and schooling. With their massive knowledge of magic, they know exactly how to shape magical forces to do what they desire. Most of the time, wizards tend to walk down a school of magic to master in.")
                .skillProficiencies(Map.of(2, Set.of(ARCANA, HISTORY, INSIGHT,
                        INVESTIGATION, MEDICINE, RELIGION)))
                .hitDice(DiceTypeEnum.D6)
                .savingThrows(Set.of(INTELLIGENCE, WISDOM))
                .armorTraining(Set.of())
                .weaponTraining(Set.of(SIMPLE))
                .startingEquipmentA(Set.of(
                        item("Robe", 1),
                        item("Arcane Focus (Quarterstaff)", 1),
                        item("Dagger", 2),
                        item("Scholar's Pack", 1),
                        item("Spellbook", 1),
                        item("CP", 5)
                ))
                .startingEquipmentB(Set.of(
                        item("GP", 55)
                ))
                .levelProgression(getWizardProgression())
                .custom(false)
                .build();
    }

    private Set<ClassLevelProgressionDto> getWizardProgression() {
        return Set.of(
                ClassLevelProgressionDto.builder().level(1).featureNames(Set.of("Spellcasting", "Arcane Recovery"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 2))).build(),
                ClassLevelProgressionDto.builder().level(2).featureNames(Set.of("Arcane Tradition"))
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 3))).build(),
                ClassLevelProgressionDto.builder().level(3).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 3, "spellSlots", Map.of(1, 4, 2, 2))).build(),
                ClassLevelProgressionDto.builder().level(4).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3))).build(),
                ClassLevelProgressionDto.builder().level(5).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 2))).build(),
                ClassLevelProgressionDto.builder().level(6).featureNames(Set.of("Arcane Tradition Feature"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3))).build(),
                ClassLevelProgressionDto.builder().level(7).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 1))).build(),
                ClassLevelProgressionDto.builder().level(8).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 2))).build(),
                ClassLevelProgressionDto.builder().level(9).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 4, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 1))).build(),
                ClassLevelProgressionDto.builder().level(10).featureNames(Set.of("Arcane Tradition Feature"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2))).build(),
                ClassLevelProgressionDto.builder().level(11).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(12).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1))).build(),
                ClassLevelProgressionDto.builder().level(13).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(14).featureNames(Set.of("Arcane Tradition Feature"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1))).build(),
                ClassLevelProgressionDto.builder().level(15).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(16).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1))).build(),
                ClassLevelProgressionDto.builder().level(17).featureNames(Set.of())
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 2, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(18).featureNames(Set.of("Spell Mastery"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 1, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(19).featureNames(Set.of("Ability Score Improvement"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 1, 8, 1, 9, 1))).build(),
                ClassLevelProgressionDto.builder().level(20).featureNames(Set.of("Signature Spells"))
                        .scalingValues(Map.of("cantrips", 5, "spellSlots", Map.of(1, 4, 2, 3, 3, 3, 4, 3, 5, 3, 6, 2, 7, 2, 8, 1, 9, 1))).build()
        );
    }

    // ==================== FEATURE DEFINITIONS ====================

    public List<FeatureEntity> createAllFeatures() {
        return List.of(
                // ===== SHARED/MULTI-CLASS FEATURES =====
                FeatureEntity.builder().name("Ability Score Improvement").classLevels(null)
                        .description("You can increase one ability score by 2, or two ability scores by 1. You can't increase an ability score above 20. Alternatively, you can take a Feat.").custom(false).build(),
                FeatureEntity.builder().name("Extra Attack").classLevels(Map.of("Barbarian", 5, "Fighter", 5, "Monk", 5, "Paladin", 5, "Ranger", 5))
                        .description("You can attack twice, instead of once, whenever you take the Attack action on your turn.").custom(false).build(),
                FeatureEntity.builder().name("Spellcasting").classLevels(Map.of("Bard", 1, "Cleric", 1, "Druid", 1, "Paladin", 2, "Ranger", 2, "Sorcerer", 1, "Wizard", 1))
                        .description("You have learned to cast spells. See chapter 10 for the general rules of spellcasting.").custom(false).build(),
                FeatureEntity.builder().name("Fighting Style").classLevels(Map.of("Fighter", 1, "Paladin", 2, "Ranger", 2))
                        .description("You adopt a particular style of fighting as your specialty.").custom(false).build(),
                FeatureEntity.builder().name("Expertise").classLevels(Map.of("Bard", 3, "Rogue", 1))
                        .description("Choose two skills you're proficient in. Your proficiency bonus is doubled for those skills.").custom(false).build(),
                FeatureEntity.builder().name("Evasion").classLevels(Map.of("Monk", 7, "Rogue", 7))
                        .description("When subjected to an effect that allows a Dex save for half damage, you take none on success and half on failure.").custom(false).build(),
                FeatureEntity.builder().name("Timeless Body").classLevels(Map.of("Druid", 18, "Monk", 15))
                        .description("You age more slowly and suffer none of the frailty of old age.").custom(false).build(),
                FeatureEntity.builder().name("Unarmored Defense").classLevels(Map.of("Barbarian", 1, "Monk", 1))
                        .description("While not wearing armor, your AC equals 10 + Dex modifier + Con/Wis modifier (depending on class).").custom(false).build(),

                // ===== BARBARIAN FEATURES =====
                FeatureEntity.builder().name("Rage").classLevels(Map.of("Barbarian", 1))
                        .description("In battle, you fight with primal ferocity. On your turn, you can enter a rage as a Bonus Action.").custom(false).build(),
                FeatureEntity.builder().name("Reckless Attack").classLevels(Map.of("Barbarian", 2))
                        .description("You can attack recklessly, gaining advantage on melee attacks but giving enemies advantage against you.").custom(false).build(),
                FeatureEntity.builder().name("Danger Sense").classLevels(Map.of("Barbarian", 2))
                        .description("You have advantage on Dexterity saving throws against effects you can see.").custom(false).build(),
                FeatureEntity.builder().name("Primal Path").classLevels(Map.of("Barbarian", 3))
                        .description("You choose a path that shapes your rage.").custom(false).build(),
                FeatureEntity.builder().name("Path Feature").classLevels(Map.of("Barbarian", 6))
                        .description("You gain a feature from your Primal Path.").custom(false).build(),
                FeatureEntity.builder().name("Fast Movement").classLevels(Map.of("Barbarian", 5))
                        .description("Your speed increases by 10 feet while not wearing heavy armor.").custom(false).build(),
                FeatureEntity.builder().name("Feral Instinct").classLevels(Map.of("Barbarian", 7))
                        .description("You have advantage on initiative rolls.").custom(false).build(),
                FeatureEntity.builder().name("Brutal Critical").classLevels(Map.of("Barbarian", 9))
                        .description("You can roll additional weapon damage dice on a critical hit.").custom(false).build(),
                FeatureEntity.builder().name("Relentless Rage").classLevels(Map.of("Barbarian", 11))
                        .description("Your rage can keep you fighting despite grievous wounds.").custom(false).build(),
                FeatureEntity.builder().name("Persistent Rage").classLevels(Map.of("Barbarian", 15))
                        .description("Your rage ends early only if you fall unconscious or choose to end it.").custom(false).build(),
                FeatureEntity.builder().name("Indomitable Might").classLevels(Map.of("Barbarian", 18))
                        .description("If your Strength check total is less than your Strength score, you can use that score instead.").custom(false).build(),
                FeatureEntity.builder().name("Primal Champion").classLevels(Map.of("Barbarian", 20))
                        .description("Your Strength and Constitution scores increase by 4. Your maximum is now 24.").custom(false).build(),

                // ===== BARD FEATURES =====
                FeatureEntity.builder().name("Bardic Inspiration").classLevels(Map.of("Bard", 1))
                        .description("You can inspire others through stirring words or music, granting a Bardic Inspiration die.").custom(false).build(),
                FeatureEntity.builder().name("Jack of All Trades").classLevels(Map.of("Bard", 2))
                        .description("You can add half your proficiency bonus to any ability check you aren't proficient in.").custom(false).build(),
                FeatureEntity.builder().name("Song of Rest").classLevels(Map.of("Bard", 2))
                        .description("You can use soothing music or oration to help revitalize wounded allies during a short rest.").custom(false).build(),
                FeatureEntity.builder().name("Bard College").classLevels(Map.of("Bard", 3))
                        .description("You choose a college that shapes your bardic abilities.").custom(false).build(),
                FeatureEntity.builder().name("Bard College Feature").classLevels(Map.of("Bard", 6))
                        .description("You gain a feature from your Bard College.").custom(false).build(),
                FeatureEntity.builder().name("Font of Inspiration").classLevels(Map.of("Bard", 5))
                        .description("You regain all expended Bardic Inspiration dice on a short or long rest.").custom(false).build(),
                FeatureEntity.builder().name("Countercharm").classLevels(Map.of("Bard", 6))
                        .description("You can use music to disrupt mind-influencing effects.").custom(false).build(),
                FeatureEntity.builder().name("Magical Secrets").classLevels(Map.of("Bard", 10))
                        .description("You learn two spells from any class's spell list.").custom(false).build(),
                FeatureEntity.builder().name("Superior Inspiration").classLevels(Map.of("Bard", 20))
                        .description("When you roll initiative and have no Bardic Inspiration dice, you regain one.").custom(false).build(),

                // ===== CLERIC FEATURES =====
                FeatureEntity.builder().name("Divine Domain").classLevels(Map.of("Cleric", 1))
                        .description("You choose a domain related to your deity.").custom(false).build(),
                FeatureEntity.builder().name("Divine Domain Feature").classLevels(Map.of("Cleric", 2))
                        .description("You gain a feature from your Divine Domain.").custom(false).build(),
                FeatureEntity.builder().name("Channel Divinity").classLevels(Map.of("Cleric", 2))
                        .description("You gain the ability to channel divine energy directly from your deity.").custom(false).build(),
                FeatureEntity.builder().name("Destroy Undead").classLevels(Map.of("Cleric", 5))
                        .description("When an undead fails its saving throw against Turn Undead, it is instantly destroyed if its CR is low enough.").custom(false).build(),
                FeatureEntity.builder().name("Divine Intervention").classLevels(Map.of("Cleric", 10))
                        .description("You can call on your deity to intervene on your behalf.").custom(false).build(),
                FeatureEntity.builder().name("Divine Intervention Improvement").classLevels(Map.of("Cleric", 20))
                        .description("Your call for divine intervention succeeds automatically.").custom(false).build(),

                // ===== DRUID FEATURES =====
                FeatureEntity.builder().name("Druidic").classLevels(Map.of("Druid", 1))
                        .description("You know Druidic, the secret language of druids.").custom(false).build(),
                FeatureEntity.builder().name("Wild Shape").classLevels(Map.of("Druid", 2))
                        .description("You can magically assume the shape of a beast.").custom(false).build(),
                FeatureEntity.builder().name("Wild Shape Improvement").classLevels(Map.of("Druid", 4))
                        .description("Your Wild Shape improves, allowing higher CR beasts.").custom(false).build(),
                FeatureEntity.builder().name("Druid Circle").classLevels(Map.of("Druid", 2))
                        .description("You choose a druid circle.").custom(false).build(),
                FeatureEntity.builder().name("Druid Circle Feature").classLevels(Map.of("Druid", 6))
                        .description("You gain a feature from your Druid Circle.").custom(false).build(),
                FeatureEntity.builder().name("Beast Spells").classLevels(Map.of("Druid", 18))
                        .description("You can cast spells while in Wild Shape.").custom(false).build(),
                FeatureEntity.builder().name("Archdruid").classLevels(Map.of("Druid", 20))
                        .description("You can use Wild Shape an unlimited number of times.").custom(false).build(),

                // ===== FIGHTER FEATURES =====
                FeatureEntity.builder().name("Second Wind").classLevels(Map.of("Fighter", 1))
                        .description("You can use a bonus action to regain hit points equal to 1d10 + your fighter level.").custom(false).build(),
                FeatureEntity.builder().name("Action Surge").classLevels(Map.of("Fighter", 2))
                        .description("You can take one additional action on your turn.").custom(false).build(),
                FeatureEntity.builder().name("Martial Archetype").classLevels(Map.of("Fighter", 3))
                        .description("You choose an archetype that you strive to emulate.").custom(false).build(),
                FeatureEntity.builder().name("Martial Archetype Feature").classLevels(Map.of("Fighter", 7))
                        .description("You gain a feature from your Martial Archetype.").custom(false).build(),
                FeatureEntity.builder().name("Indomitable").classLevels(Map.of("Fighter", 9))
                        .description("You can reroll a saving throw that you fail.").custom(false).build(),

                // ===== MONK FEATURES =====
                FeatureEntity.builder().name("Martial Arts").classLevels(Map.of("Monk", 1))
                        .description("Your practice of martial arts gives you mastery of unarmed strikes and monk weapons.").custom(false).build(),
                FeatureEntity.builder().name("Ki").classLevels(Map.of("Monk", 2))
                        .description("Your training allows you to harness the mystic energy of ki.").custom(false).build(),
                FeatureEntity.builder().name("Unarmored Movement").classLevels(Map.of("Monk", 2))
                        .description("Your speed increases while not wearing armor or a shield.").custom(false).build(),
                FeatureEntity.builder().name("Unarmored Movement Improvement").classLevels(Map.of("Monk", 9))
                        .description("You can move along vertical surfaces and across liquids.").custom(false).build(),
                FeatureEntity.builder().name("Monastic Tradition").classLevels(Map.of("Monk", 3))
                        .description("You commit yourself to a monastic tradition.").custom(false).build(),
                FeatureEntity.builder().name("Monastic Tradition Feature").classLevels(Map.of("Monk", 6))
                        .description("You gain a feature from your Monastic Tradition.").custom(false).build(),
                FeatureEntity.builder().name("Deflect Missiles").classLevels(Map.of("Monk", 3))
                        .description("You can deflect or catch missiles when hit by a ranged weapon attack.").custom(false).build(),
                FeatureEntity.builder().name("Slow Fall").classLevels(Map.of("Monk", 4))
                        .description("You can reduce falling damage by an amount equal to five times your monk level.").custom(false).build(),
                FeatureEntity.builder().name("Stunning Strike").classLevels(Map.of("Monk", 5))
                        .description("You can interfere with the flow of ki in an opponent's body.").custom(false).build(),
                FeatureEntity.builder().name("Ki-Empowered Strikes").classLevels(Map.of("Monk", 6))
                        .description("Your unarmed strikes count as magical.").custom(false).build(),
                FeatureEntity.builder().name("Stillness of Mind").classLevels(Map.of("Monk", 7))
                        .description("You can end one effect causing you to be charmed or frightened.").custom(false).build(),
                FeatureEntity.builder().name("Purity of Body").classLevels(Map.of("Monk", 10))
                        .description("You are immune to disease and poison.").custom(false).build(),
                FeatureEntity.builder().name("Tongue of the Sun and Moon").classLevels(Map.of("Monk", 13))
                        .description("You can understand all spoken languages and any creature can understand you.").custom(false).build(),
                FeatureEntity.builder().name("Diamond Soul").classLevels(Map.of("Monk", 14))
                        .description("You gain proficiency in all saving throws.").custom(false).build(),
                FeatureEntity.builder().name("Empty Body").classLevels(Map.of("Monk", 18))
                        .description("You can use ki to become invisible or cast astral projection.").custom(false).build(),
                FeatureEntity.builder().name("Perfect Self").classLevels(Map.of("Monk", 20))
                        .description("When you roll initiative with no ki points, you regain 4.").custom(false).build(),

                // ===== PALADIN FEATURES =====
                FeatureEntity.builder().name("Divine Sense").classLevels(Map.of("Paladin", 1))
                        .description("You can detect celestials, fiends, and undead within 60 feet.").custom(false).build(),
                FeatureEntity.builder().name("Lay on Hands").classLevels(Map.of("Paladin", 1))
                        .description("You have a pool of healing power that replenishes on a long rest.").custom(false).build(),
                FeatureEntity.builder().name("Divine Smite").classLevels(Map.of("Paladin", 2))
                        .description("When you hit with a melee weapon, you can expend a spell slot to deal extra radiant damage.").custom(false).build(),
                FeatureEntity.builder().name("Divine Health").classLevels(Map.of("Paladin", 3))
                        .description("You are immune to disease.").custom(false).build(),
                FeatureEntity.builder().name("Sacred Oath").classLevels(Map.of("Paladin", 3))
                        .description("You swear an oath that binds you as a paladin forever.").custom(false).build(),
                FeatureEntity.builder().name("Sacred Oath Feature").classLevels(Map.of("Paladin", 7))
                        .description("You gain a feature from your Sacred Oath.").custom(false).build(),
                FeatureEntity.builder().name("Aura of Protection").classLevels(Map.of("Paladin", 6))
                        .description("Friendly creatures within 10 feet add your Charisma modifier to saving throws.").custom(false).build(),
                FeatureEntity.builder().name("Aura of Courage").classLevels(Map.of("Paladin", 10))
                        .description("You and friendly creatures within 10 feet can't be frightened.").custom(false).build(),
                FeatureEntity.builder().name("Improved Divine Smite").classLevels(Map.of("Paladin", 11))
                        .description("All melee weapon hits deal an extra 1d8 radiant damage.").custom(false).build(),
                FeatureEntity.builder().name("Cleansing Touch").classLevels(Map.of("Paladin", 14))
                        .description("You can end one spell on yourself or a willing creature.").custom(false).build(),
                FeatureEntity.builder().name("Aura Improvements").classLevels(Map.of("Paladin", 18))
                        .description("Your auras increase to 30 feet.").custom(false).build(),

                // ===== RANGER FEATURES =====
                FeatureEntity.builder().name("Favored Enemy").classLevels(Map.of("Ranger", 1))
                        .description("You have significant experience hunting a certain type of enemy.").custom(false).build(),
                FeatureEntity.builder().name("Favored Enemy Improvement").classLevels(Map.of("Ranger", 6))
                        .description("You choose an additional favored enemy.").custom(false).build(),
                FeatureEntity.builder().name("Natural Explorer").classLevels(Map.of("Ranger", 1))
                        .description("You are adept at traveling and surviving in certain types of terrain.").custom(false).build(),
                FeatureEntity.builder().name("Natural Explorer Improvement").classLevels(Map.of("Ranger", 6))
                        .description("You choose an additional favored terrain.").custom(false).build(),
                FeatureEntity.builder().name("Ranger Archetype").classLevels(Map.of("Ranger", 3))
                        .description("You choose an archetype that you strive to emulate.").custom(false).build(),
                FeatureEntity.builder().name("Ranger Archetype Feature").classLevels(Map.of("Ranger", 7))
                        .description("You gain a feature from your Ranger Archetype.").custom(false).build(),
                FeatureEntity.builder().name("Primeval Awareness").classLevels(Map.of("Ranger", 3))
                        .description("You can sense aberrations, celestials, dragons, elementals, fey, fiends, and undead.").custom(false).build(),
                FeatureEntity.builder().name("Land's Stride").classLevels(Map.of("Ranger", 8))
                        .description("Moving through nonmagical difficult terrain costs no extra movement.").custom(false).build(),
                FeatureEntity.builder().name("Hide in Plain Sight").classLevels(Map.of("Ranger", 10))
                        .description("You can create camouflage, gaining +10 to Stealth.").custom(false).build(),
                FeatureEntity.builder().name("Vanish").classLevels(Map.of("Ranger", 14))
                        .description("You can use Hide as a bonus action.").custom(false).build(),
                FeatureEntity.builder().name("Feral Senses").classLevels(Map.of("Ranger", 18))
                        .description("You gain preternatural senses against invisible creatures.").custom(false).build(),
                FeatureEntity.builder().name("Foe Slayer").classLevels(Map.of("Ranger", 20))
                        .description("Add your Wisdom modifier to attack or damage against a favored enemy.").custom(false).build(),

                // ===== ROGUE FEATURES =====
                FeatureEntity.builder().name("Sneak Attack").classLevels(Map.of("Rogue", 1))
                        .description("Deal extra damage when you have advantage or an ally is adjacent to the target.").custom(false).build(),
                FeatureEntity.builder().name("Thieves' Cant").classLevels(Map.of("Rogue", 1))
                        .description("You know thieves' cant, a secret mix of dialect, jargon, and code.").custom(false).build(),
                FeatureEntity.builder().name("Cunning Action").classLevels(Map.of("Rogue", 2))
                        .description("You can use a bonus action to Dash, Disengage, or Hide.").custom(false).build(),
                FeatureEntity.builder().name("Roguish Archetype").classLevels(Map.of("Rogue", 3))
                        .description("You choose an archetype that you emulate.").custom(false).build(),
                FeatureEntity.builder().name("Roguish Archetype Feature").classLevels(Map.of("Rogue", 9))
                        .description("You gain a feature from your Roguish Archetype.").custom(false).build(),
                FeatureEntity.builder().name("Uncanny Dodge").classLevels(Map.of("Rogue", 5))
                        .description("When an attacker you can see hits you, you can halve the damage.").custom(false).build(),
                FeatureEntity.builder().name("Reliable Talent").classLevels(Map.of("Rogue", 11))
                        .description("Treat any d20 roll of 9 or lower as a 10 for proficient ability checks.").custom(false).build(),
                FeatureEntity.builder().name("Blindsense").classLevels(Map.of("Rogue", 14))
                        .description("You can sense any hidden or invisible creature within 10 feet.").custom(false).build(),
                FeatureEntity.builder().name("Slippery Mind").classLevels(Map.of("Rogue", 15))
                        .description("You gain proficiency in Wisdom saving throws.").custom(false).build(),
                FeatureEntity.builder().name("Elusive").classLevels(Map.of("Rogue", 18))
                        .description("No attack roll has advantage against you while you aren't incapacitated.").custom(false).build(),
                FeatureEntity.builder().name("Stroke of Luck").classLevels(Map.of("Rogue", 20))
                        .description("Turn a miss into a hit, or a failed ability check into a 20.").custom(false).build(),

                // ===== SORCERER FEATURES =====
                FeatureEntity.builder().name("Sorcerous Origin").classLevels(Map.of("Sorcerer", 1))
                        .description("Choose a sorcerous origin that describes the source of your innate magical power.").custom(false).build(),
                FeatureEntity.builder().name("Sorcerous Origin Feature").classLevels(Map.of("Sorcerer", 6))
                        .description("You gain a feature from your Sorcerous Origin.").custom(false).build(),
                FeatureEntity.builder().name("Font of Magic").classLevels(Map.of("Sorcerer", 2))
                        .description("You tap into a deep wellspring of magic, represented by sorcery points.").custom(false).build(),
                FeatureEntity.builder().name("Metamagic").classLevels(Map.of("Sorcerer", 3))
                        .description("You gain the ability to twist your spells to suit your needs.").custom(false).build(),
                FeatureEntity.builder().name("Sorcerous Restoration").classLevels(Map.of("Sorcerer", 20))
                        .description("You regain 4 sorcery points whenever you finish a short rest.").custom(false).build(),

                // ===== WARLOCK FEATURES =====
                FeatureEntity.builder().name("Otherworldly Patron").classLevels(Map.of("Warlock", 1))
                        .description("You have struck a bargain with an otherworldly being.").custom(false).build(),
                FeatureEntity.builder().name("Otherworldly Patron Feature").classLevels(Map.of("Warlock", 6))
                        .description("You gain a feature from your Otherworldly Patron.").custom(false).build(),
                FeatureEntity.builder().name("Pact Magic").classLevels(Map.of("Warlock", 1))
                        .description("Your arcane research and patron magic have given you facility with spells.").custom(false).build(),
                FeatureEntity.builder().name("Eldritch Invocations").classLevels(Map.of("Warlock", 2))
                        .description("You learn fragments of forbidden knowledge that imbue you with abilities.").custom(false).build(),
                FeatureEntity.builder().name("Pact Boon").classLevels(Map.of("Warlock", 3))
                        .description("Your patron bestows a gift upon you for your loyal service.").custom(false).build(),
                FeatureEntity.builder().name("Mystic Arcanum").classLevels(Map.of("Warlock", 11))
                        .description("Your patron bestows upon you a magical secret called an arcanum.").custom(false).build(),
                FeatureEntity.builder().name("Eldritch Master").classLevels(Map.of("Warlock", 20))
                        .description("You can entreat your patron to regain all spell slots.").custom(false).build(),

                // ===== WIZARD FEATURES =====
                FeatureEntity.builder().name("Arcane Recovery").classLevels(Map.of("Wizard", 1))
                        .description("You can regain some spell slots during a short rest.").custom(false).build(),
                FeatureEntity.builder().name("Arcane Tradition").classLevels(Map.of("Wizard", 2))
                        .description("You choose an arcane tradition, shaping your practice of magic.").custom(false).build(),
                FeatureEntity.builder().name("Arcane Tradition Feature").classLevels(Map.of("Wizard", 6))
                        .description("You gain a feature from your Arcane Tradition.").custom(false).build(),
                FeatureEntity.builder().name("Spell Mastery").classLevels(Map.of("Wizard", 18))
                        .description("You can cast certain 1st and 2nd level spells at will.").custom(false).build(),
                FeatureEntity.builder().name("Signature Spells").classLevels(Map.of("Wizard", 20))
                        .description("You gain mastery over two 3rd-level spells, casting them without expending slots.").custom(false).build()
        );
    }

    // ==================== EQUIPMENT HELPER ====================
    private StartingEquipmentItemDto item(String equipmentName, int quantity) {
        return StartingEquipmentItemDto.builder()
                .equipmentName(equipmentName)
                .quantity(quantity)
                .build();
    }
}
