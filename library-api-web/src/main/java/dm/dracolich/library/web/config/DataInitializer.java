package dm.dracolich.library.web.config;

import dm.dracolich.library.dto.enums.AbilityTypeEnum;
import dm.dracolich.library.dto.enums.AttackTypeEnum;
import dm.dracolich.library.dto.enums.CoinEnum;
import dm.dracolich.library.dto.enums.DamageTypeEnum;
import dm.dracolich.library.dto.enums.EquipmentCategoryEnum;
import dm.dracolich.library.dto.enums.SpellTypeEnum;
import dm.dracolich.library.web.config.initializer.ClassInitializer;
import dm.dracolich.library.web.config.initializer.SubclassInitializer;
import dm.dracolich.library.web.entity.*;
import dm.dracolich.library.web.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    private final ClassInitializer classInitializer;
    private final SubclassInitializer subclassInitializer;
    private final AttributeRepository attributeRepository;
    private final RaceRepository raceRepository;
    private final SubclassRepository subclassRepository;
    private final AlignmentRepository alignmentRepository;
    private final BackgroundRepository backgroundRepository;
    private final FeatureRepository featureRepository;
    private final SubraceRepository subraceRepository;
    private final SpellRepository spellRepository;
    private final EquipmentRepository equipmentRepository;
    private final ClassRepository classRepository;

    private final Map<String, ClassEntity> classCache = new HashMap<>();
    private final Map<String, AttributeEntity> attributeCache = new HashMap<>();
    private final Map<String, RaceEntity> raceCache = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        log.info("Starting database initialization...");

        attributeRepository.count()
                .flatMap(count -> {
                    if (count > 0) {
                        log.info("Database already contains data, skipping initialization");
                        return Mono.just("skipped");
                    }
                    return seedAllData().thenReturn("completed");
                })
                .subscribe(
                        result -> log.info("Database initialization {}", result),
                        error -> log.error("Database initialization failed", error)
                );
    }

    private Mono<Void> seedAllData() {
        return seedAttributes()
                .then(Mono.defer(this::seedRaces))
                .then(Mono.defer(this::seedClasses))
                .then(Mono.defer(this::seedSubclasses))
                .then(Mono.defer(this::seedAlignments))
                .then(Mono.defer(this::seedBackgrounds))
                .then(Mono.defer(this::seedFeatures))
                .then(Mono.defer(this::seedSubraces))
                .then(Mono.defer(this::seedSpells))
                .then(Mono.defer(this::seedEquipment))
                .then(Mono.fromRunnable(() -> log.info("All seed data inserted successfully")));
    }

    private Mono<Void> seedAttributes() {
        log.info("Seeding attributes...");
        List<AttributeEntity> attributes = createAttributes();
        return attributeRepository.saveAll(attributes)
                .doOnNext(attr -> attributeCache.put(attr.getName(), attr))
                .then()
                .doOnSuccess(v -> log.info("Seeded {} attributes", attributes.size()));
    }

    private Mono<Void> seedRaces() {
        log.info("Seeding races...");
        List<RaceEntity> races = createRaces();
        return raceRepository.saveAll(races)
                .doOnNext(race -> raceCache.put(race.getName(), race))
                .then()
                .doOnSuccess(v -> log.info("Seeded {} races", races.size()));
    }

    public Mono<Void> seedClasses() {
        log.info("Seeding classes...");
        List<ClassEntity> classes = classInitializer.createClasses();
        return classRepository.saveAll(classes)
                .doOnNext(cls -> classCache.put(cls.getName(), cls))
                .then()
                .doOnSuccess(v -> log.info("Seeded {} classes", classes.size()));
    }

    private Mono<Void> seedSubclasses() {
        log.info("Seeding subclasses...");
        List<SubclassEntity> subclasses = subclassInitializer.createSubclasses(classCache);
        return subclassRepository.saveAll(subclasses)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} subclasses", subclasses.size()));
    }

    private Mono<Void> seedAlignments() {
        log.info("Seeding alignments...");
        List<AlignmentEntity> alignments = createAlignments();
        return alignmentRepository.saveAll(alignments)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} alignments", alignments.size()));
    }

    private Mono<Void> seedBackgrounds() {
        log.info("Seeding backgrounds...");
        List<BackgroundEntity> backgrounds = createBackgrounds();
        return backgroundRepository.saveAll(backgrounds)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} backgrounds", backgrounds.size()));
    }

    private Mono<Void> seedFeatures() {
        log.info("Seeding features...");
        List<FeatureEntity> features = classInitializer.createAllFeatures();
        return featureRepository.saveAll(features)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} features", features.size()));
    }

    private Mono<Void> seedSubraces() {
        log.info("Seeding subraces...");
        List<SubraceEntity> subraces = createSubraces();
        return subraceRepository.saveAll(subraces)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} subraces", subraces.size()));
    }

    // ==================== ATTRIBUTE CREATION ====================
    private List<AttributeEntity> createAttributes() {
        List<AttributeEntity> attributes = new ArrayList<>();

        // Race Attributes
        attributes.add(AttributeEntity.builder()
                .name("Celestial Resistance")
                .description("You have resistance to necrotic and radiant damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Darkvision")
                .description("You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Healing Hands")
                .description("As an action, you can touch a creature and roll a number of d4s equal to your proficiency bonus. The creature regains hit points equal to the total rolled.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Light Bearer")
                .description("You know the Light cantrip. Charisma is your spellcasting ability for it.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Celestial Revelation")
                .description("You can manifest celestial features. Choose one: Heavenly Wings, Inner Radiance, or Necrotic Shroud.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Dragon Ancestry")
                .description("You have draconic ancestry. Choose one type of dragon from the table. Your breath weapon and damage resistance are determined by the dragon type.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Breath Weapon")
                .description("You can use your action to exhale destructive energy. Your draconic ancestry determines the size, shape, and damage type of the exhalation.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Damage Resistance")
                .description("You have resistance to the damage type associated with your draconic ancestry.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Flight")
                .description("You have a flying speed equal to your walking speed. You can't use this flying speed if you're wearing medium or heavy armor.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Dwarven Resilience")
                .description("You have advantage on saving throws against poison, and you have resistance against poison damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Dwarven Toughness")
                .description("Your hit point maximum increases by 1, and it increases by 1 every time you gain a level.")
                .abilityBonus(Map.of(AbilityTypeEnum.CONSTITUTION, 1))
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Stonecunning")
                .description("Whenever you make an Intelligence (History) check related to the origin of stonework, you are considered proficient and add double your proficiency bonus.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Elven Lineage")
                .description("You are part of a lineage that grants you supernatural abilities. Choose High Elf, Wood Elf, or Dark Elf lineage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Fey Ancestry")
                .description("You have advantage on saving throws against being charmed, and magic can't put you to sleep.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Keen Senses")
                .description("You have proficiency in the Perception skill.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Trance")
                .description("Elves don't need to sleep. Instead, they meditate deeply for 4 hours a day, gaining the same benefit that a human does from 8 hours of sleep.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Gnomish Cunning")
                .description("You have advantage on Intelligence, Wisdom, and Charisma saving throws against magic.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Gnomish Lineage")
                .description("You are part of a lineage that grants you supernatural abilities. Choose Forest Gnome or Rock Gnome lineage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Giant Ancestry")
                .description("You are descended from Giants. You gain the ability to use certain giant-related abilities based on your subrace.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Large Form")
                .description("Starting at 5th level, you can change your size to Large as a bonus action. This lasts for 10 minutes or until you end it as a bonus action.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Powerful Build")
                .description("You count as one size larger when determining your carrying capacity and the weight you can push, drag, or lift.")
                .abilityBonus(Map.of(AbilityTypeEnum.STRENGTH, 1))
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Brave")
                .description("You have advantage on saving throws against being frightened.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Halfling Nimbleness")
                .description("You can move through the space of any creature that is of a size larger than yours.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Luck")
                .description("When you roll a 1 on the d20 for an attack roll, ability check, or saving throw, you can reroll the die and must use the new roll.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Stealthy")
                .description("You have proficiency in the Stealth skill.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Resourceful")
                .description("You gain Heroic Inspiration whenever you finish a long rest.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Skillful")
                .description("You gain proficiency in one skill of your choice.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Versatile")
                .description("You gain an Origin feat of your choice.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Adrenaline Rush")
                .description("You can take the Dash action as a bonus action. When you do, you gain temporary hit points equal to your proficiency bonus.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Relentless")
                .description("When you are reduced to 0 hit points but not killed outright, you can drop to 1 hit point instead. You can't use this feature again until you finish a long rest.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Endurance")
                .description("You have proficiency in the Survival skill, and you have advantage on Constitution saving throws against exhaustion.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Fiendish Legacy")
                .description("You are the recipient of a fiendish legacy that grants you supernatural abilities. Choose Abyssal, Chthonic, or Infernal legacy.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Presence")
                .description("You have proficiency in one of the following skills of your choice: Deception, Intimidation, or Persuasion.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Vampiric Bite")
                .description("Your fanged bite is a natural weapon, which counts as a simple melee weapon. You can use it to make unarmed strikes dealing 1d4 piercing damage + Constitution modifier.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Fire Resistance")
                .description("You have resistance to fire damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Cold Resistance")
                .description("You have resistance to cold damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Misty Step")
                .description("You can cast Misty Step without expending a spell slot. You can use this ability a number of times equal to your proficiency bonus, regaining all uses after a long rest.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Astral Knowledge")
                .description("You can mystically access a reservoir of experiences of entities connected to the Astral Plane. You gain proficiency in one skill of your choice.")
                .abilityBonus(Map.of(AbilityTypeEnum.INTELLIGENCE, 1))
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Psychic Resistance")
                .description("You have resistance to psychic damage.")
                .build());

        // Class Features
        attributes.add(AttributeEntity.builder()
                .name("Magical Tinkering")
                .description("You've learned how to invest a spark of magic into mundane objects. You can touch a Tiny nonmagical object and imbue it with one magical property.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Infuse Item")
                .description("You've gained the ability to imbue mundane items with certain magical infusions, turning them into magic items.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Rage")
                .description("In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action, granting advantage on Strength checks and saving throws, bonus rage damage, and resistance to bludgeoning, piercing, and slashing damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Unarmored Defense")
                .description("While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier (Barbarian) or Wisdom modifier (Monk).")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Reckless Attack")
                .description("You can throw aside all concern for defense to attack with fierce desperation. When you make your first attack on your turn, you can decide to attack recklessly, giving you advantage on melee weapon attack rolls but giving attack rolls against you advantage until your next turn.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Bardic Inspiration")
                .description("You can inspire others through stirring words or music. A creature within 60 feet that can hear you gains a Bardic Inspiration die to add to one ability check, attack roll, or saving throw.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Jack of All Trades")
                .description("You can add half your proficiency bonus, rounded down, to any ability check you make that doesn't already include your proficiency bonus.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Song of Rest")
                .description("You can use soothing music or oration to help revitalize your wounded allies during a short rest. Creatures that regain hit points by spending Hit Dice gain extra healing.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Spellcasting")
                .description("You have learned to cast spells through study, devotion, or innate ability. You have a pool of spell slots that you use to cast your spells.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Channel Divinity")
                .description("You gain the ability to channel divine energy directly from your deity, using that energy to fuel magical effects. You start with two such effects: Turn Undead and an effect determined by your domain.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Divine Intervention")
                .description("You can call on your deity to intervene on your behalf when your need is great. Imploring your deity's aid requires you to use your action.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Wild Shape")
                .description("You can use your action to magically assume the shape of a beast that you have seen before. You can use this feature twice and regain expended uses when you finish a short or long rest.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Fighting Style")
                .description("You adopt a particular style of fighting as your specialty. Choose one Fighting Style option such as Archery, Defense, Dueling, or Great Weapon Fighting.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Second Wind")
                .description("You have a limited well of stamina that you can draw on to protect yourself from harm. On your turn, you can use a bonus action to regain hit points equal to 1d10 + your fighter level.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Action Surge")
                .description("You can push yourself beyond your normal limits for a moment. On your turn, you can take one additional action.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Extra Attack")
                .description("You can attack twice, instead of once, whenever you take the Attack action on your turn.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Martial Arts")
                .description("Your practice of martial arts gives you mastery of combat styles that use unarmed strikes and monk weapons. You gain benefits while unarmed or wielding only monk weapons.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Ki")
                .description("Your training allows you to harness the mystic energy of ki. You have a number of ki points equal to your monk level that you can spend on various ki features.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Deflect Missiles")
                .description("You can use your reaction to deflect or catch the missile when you are hit by a ranged weapon attack. You reduce the damage by 1d10 + your Dexterity modifier + your monk level.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Divine Sense")
                .description("The presence of strong evil registers on your senses like a noxious odor, and powerful good rings like heavenly music in your ears. You can detect celestials, fiends, and undead within 60 feet.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Lay on Hands")
                .description("Your blessed touch can heal wounds. You have a pool of healing power that replenishes when you take a long rest. You can restore a total number of hit points equal to your paladin level × 5.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Divine Smite")
                .description("When you hit a creature with a melee weapon attack, you can expend one spell slot to deal radiant damage to the target, in addition to the weapon's damage.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Favored Enemy")
                .description("You have significant experience studying, tracking, hunting, and even talking to a certain type of enemy. Choose a type of favored enemy. You have advantage on Survival checks to track them and Intelligence checks to recall information about them.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Natural Explorer")
                .description("You are particularly familiar with one type of natural environment and are adept at traveling and surviving in such regions. Choose one type of favored terrain.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Primeval Awareness")
                .description("You can use your action and expend one ranger spell slot to focus your awareness on the region around you. You sense whether certain creature types are present within 1 mile (or 6 miles in favored terrain).")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Sneak Attack")
                .description("You know how to strike subtly and exploit a foe's distraction. Once per turn, you can deal extra damage to one creature you hit with an attack if you have advantage or an ally is within 5 feet of the target.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Thieves Cant")
                .description("During your rogue training you learned thieves' cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Cunning Action")
                .description("You can take a bonus action on each of your turns in combat to take the Dash, Disengage, or Hide action.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Evasion")
                .description("When you are subjected to an effect that allows you to make a Dexterity saving throw to take only half damage, you instead take no damage on a success and only half damage on a failure.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Sorcery Points")
                .description("You have a pool of sorcery points that you can use to create spell slots, fuel Metamagic options, and power other sorcerer abilities.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Metamagic")
                .description("You gain the ability to twist your spells to suit your needs. You gain Metamagic options like Careful Spell, Distant Spell, Empowered Spell, Extended Spell, Quickened Spell, Subtle Spell, and Twinned Spell.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Eldritch Invocations")
                .description("In your study of occult lore, you have unearthed eldritch invocations, fragments of forbidden knowledge that imbue you with an abiding magical ability.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Pact Boon")
                .description("Your otherworldly patron bestows a gift upon you for your loyal service. You gain one of the following features: Pact of the Blade, Pact of the Chain, or Pact of the Tome.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Arcane Recovery")
                .description("You have learned to regain some of your magical energy by studying your spellbook. Once per day when you finish a short rest, you can recover spell slots with a combined level equal to or less than half your wizard level.")
                .build());
        attributes.add(AttributeEntity.builder()
                .name("Spell Mastery")
                .description("You have achieved such mastery over certain spells that you can cast them at will. Choose a 1st-level wizard spell and a 2nd-level wizard spell. You can cast those spells at their lowest level without expending a spell slot.")
                .build());

        return attributes;
    }

    // ==================== RACE CREATION ====================
    private List<RaceEntity> createRaces() {
        return List.of(
                // Aasimar: Celestial Resistance, Darkvision, Healing Hands, Light Bearer, Celestial Revelation
                // +2 CHA
                RaceEntity.builder()
                        .name("Aasimar")
                        .raceAttributes(getAttributeSet("Celestial Resistance", "Darkvision", "Healing Hands", "Light Bearer", "Celestial Revelation"))
                        .abilityBonus(Map.of(AbilityTypeEnum.CHARISMA, 2))
                        .custom(false)
                        .build(),

                // Dragonborn: Dragon Ancestry, Breath Weapon, Damage Resistance, Darkvision, Flight
                // +2 STR, +1 CHA
                RaceEntity.builder()
                        .name("Dragonborn")
                        .raceAttributes(getAttributeSet("Dragon Ancestry", "Breath Weapon", "Damage Resistance", "Darkvision", "Flight"))
                        .abilityBonus(Map.of(AbilityTypeEnum.STRENGTH, 2, AbilityTypeEnum.CHARISMA, 1))
                        .custom(false)
                        .build(),

                // Dwarf: Darkvision, Dwarven Resilience, Dwarven Toughness, Stonecunning
                // +2 CON
                RaceEntity.builder()
                        .name("Dwarf")
                        .raceAttributes(getAttributeSet("Darkvision", "Dwarven Resilience", "Dwarven Toughness", "Stonecunning"))
                        .abilityBonus(Map.of(AbilityTypeEnum.CONSTITUTION, 2))
                        .custom(false)
                        .build(),

                // Elf: Darkvision, Elven Lineage, Fey Ancestry, Keen Senses, Trance
                // +2 DEX
                RaceEntity.builder()
                        .name("Elf")
                        .raceAttributes(getAttributeSet("Darkvision", "Elven Lineage", "Fey Ancestry", "Keen Senses", "Trance"))
                        .abilityBonus(Map.of(AbilityTypeEnum.DEXTERITY, 2))
                        .custom(false)
                        .build(),

                // Gnome: Darkvision, Gnomish Cunning, Gnomish Lineage
                // +2 INT
                RaceEntity.builder()
                        .name("Gnome")
                        .raceAttributes(getAttributeSet("Darkvision", "Gnomish Cunning", "Gnomish Lineage"))
                        .abilityBonus(Map.of(AbilityTypeEnum.INTELLIGENCE, 2))
                        .custom(false)
                        .build(),

                // Goliath: Giant Ancestry, Large Form, Powerful Build
                // +2 STR, +1 CON
                RaceEntity.builder()
                        .name("Goliath")
                        .raceAttributes(getAttributeSet("Giant Ancestry", "Large Form", "Powerful Build"))
                        .abilityBonus(Map.of(AbilityTypeEnum.STRENGTH, 2, AbilityTypeEnum.CONSTITUTION, 1))
                        .custom(false)
                        .build(),

                // Halfling: Brave, Halfling Nimbleness, Luck, Stealthy
                // +2 DEX
                RaceEntity.builder()
                        .name("Halfling")
                        .raceAttributes(getAttributeSet("Brave", "Halfling Nimbleness", "Luck", "Stealthy"))
                        .abilityBonus(Map.of(AbilityTypeEnum.DEXTERITY, 2))
                        .custom(false)
                        .build(),

                // Human: Resourceful, Skillful, Versatile
                // +1 to all abilities
                RaceEntity.builder()
                        .name("Human")
                        .raceAttributes(getAttributeSet("Resourceful", "Skillful", "Versatile"))
                        .abilityBonus(Map.of(
                                AbilityTypeEnum.STRENGTH, 1,
                                AbilityTypeEnum.DEXTERITY, 1,
                                AbilityTypeEnum.CONSTITUTION, 1,
                                AbilityTypeEnum.INTELLIGENCE, 1,
                                AbilityTypeEnum.WISDOM, 1,
                                AbilityTypeEnum.CHARISMA, 1
                        ))
                        .custom(false)
                        .build(),

                // Orc: Adrenaline Rush, Darkvision, Relentless, Endurance
                // +2 STR, +1 CON
                RaceEntity.builder()
                        .name("Orc")
                        .raceAttributes(getAttributeSet("Adrenaline Rush", "Darkvision", "Relentless", "Endurance"))
                        .abilityBonus(Map.of(AbilityTypeEnum.STRENGTH, 2, AbilityTypeEnum.CONSTITUTION, 1))
                        .custom(false)
                        .build(),

                // Tiefling: Darkvision, Fiendish Legacy, Presence, Fire Resistance
                // +2 CHA, +1 INT
                RaceEntity.builder()
                        .name("Tiefling")
                        .raceAttributes(getAttributeSet("Darkvision", "Fiendish Legacy", "Presence", "Fire Resistance"))
                        .abilityBonus(Map.of(AbilityTypeEnum.CHARISMA, 2, AbilityTypeEnum.INTELLIGENCE, 1))
                        .custom(false)
                        .build(),

                // Dhampir: Darkvision, Vampiric Bite, Damage Resistance
                // No fixed ability bonuses (flexible ASI)
                RaceEntity.builder()
                        .name("Dhampir")
                        .raceAttributes(getAttributeSet("Darkvision", "Vampiric Bite", "Damage Resistance"))
                        .custom(false)
                        .build(),

                // Githyanki: Astral Knowledge, Psychic Resistance, Misty Step
                // +2 STR, +1 INT
                RaceEntity.builder()
                        .name("Githyanki")
                        .raceAttributes(getAttributeSet("Astral Knowledge", "Psychic Resistance", "Misty Step"))
                        .abilityBonus(Map.of(AbilityTypeEnum.STRENGTH, 2, AbilityTypeEnum.INTELLIGENCE, 1))
                        .custom(false)
                        .build(),

                // Githzerai: Psychic Resistance, Darkvision
                // +2 WIS, +1 INT
                RaceEntity.builder()
                        .name("Githzerai")
                        .raceAttributes(getAttributeSet("Psychic Resistance", "Darkvision"))
                        .abilityBonus(Map.of(AbilityTypeEnum.WISDOM, 2, AbilityTypeEnum.INTELLIGENCE, 1))
                        .custom(false)
                        .build()
        );
    }

    // ==================== ALIGNMENT CREATION ====================
    private List<AlignmentEntity> createAlignments() {
        return List.of(
                AlignmentEntity.builder().name("Lawful Good").description("Believes in honor, justice and doing the right thing as expected by society and the law.").build(),
                AlignmentEntity.builder().name("Neutral Good").description("Believes in trying to do what's right but is not bound by laws, social norms or tradition.").build(),
                AlignmentEntity.builder().name("Chaotic Good").description("Believes in following their conscience with little regard for the law or what others expect.").build(),
                AlignmentEntity.builder().name("Lawful Neutral").description("Believes in the rule of law but isn't swayed by those in need or the temptations of evil.").build(),
                AlignmentEntity.builder().name("True Neutral").description("Believes in doing what seems best at the time and avoids taking sides or moral arguments.").build(),
                AlignmentEntity.builder().name("Chaotic Neutral").description("Believes in their personal freedom and following their own desires and whims.").build(),
                AlignmentEntity.builder().name("Lawful Evil").description("Believes in taking what they want within the limits of the law or a personal code.").build(),
                AlignmentEntity.builder().name("Neutral Evil").description("Believes in taking what they want and are untroubled by who they hurt or which laws they break.").build(),
                AlignmentEntity.builder().name("Chaotic Evil").description("Believes in arbitrary violence, spurred by hatred, bloodlust or a desire for vengeance.").build()
        );
    }

    // ==================== BACKGROUND CREATION ====================
    private List<BackgroundEntity> createBackgrounds() {
        return List.of(
                BackgroundEntity.builder().name("Acolyte").description("You have spent your life in service to a temple, learning sacred rites and providing sacrifices to the god or gods you worship.").custom(false).build(),
                BackgroundEntity.builder().name("Charlatan").description("You have always had a way with people. You know what makes them tick, you can tease out their hearts' desires, and with a few leading questions you can read them like they were children's books.").custom(false).build(),
                BackgroundEntity.builder().name("Criminal").description("You are an experienced criminal with a history of breaking the law. You have spent a lot of time among other criminals and still have contacts within the criminal underworld.").custom(false).build(),
                BackgroundEntity.builder().name("Entertainer").description("You thrive in front of an audience. You know how to entrance them, entertain them, and even inspire them.").custom(false).build(),
                BackgroundEntity.builder().name("Folk Hero").description("You come from a humble social rank, but you are destined for so much more. Already the people of your home village regard you as their champion.").custom(false).build(),
                BackgroundEntity.builder().name("Guild Artisan").description("You are a member of an artisan's guild, skilled in a particular field and closely associated with other artisans.").custom(false).build(),
                BackgroundEntity.builder().name("Hermit").description("You lived in seclusion for a formative part of your life. In your time apart from the clamor of society, you found quiet, solitude, and perhaps some of the answers you were looking for.").custom(false).build(),
                BackgroundEntity.builder().name("Noble").description("You understand wealth, power, and privilege. You carry a noble title, and your family owns land, collects taxes, and wields significant political influence.").custom(false).build(),
                BackgroundEntity.builder().name("Outlander").description("You grew up in the wilds, far from civilization and the comforts of town and technology.").custom(false).build(),
                BackgroundEntity.builder().name("Sage").description("You spent years learning the lore of the multiverse. You scoured manuscripts, studied scrolls, and listened to the greatest experts on the subjects that interest you.").custom(false).build(),
                BackgroundEntity.builder().name("Sailor").description("You sailed on a seagoing vessel for years. In that time, you faced down mighty storms, monsters of the deep, and those who wanted to sink your craft to the bottomless depths.").custom(false).build(),
                BackgroundEntity.builder().name("Soldier").description("War has been your life for as long as you care to remember. You trained as a youth, studied the use of weapons and armor, learned basic survival techniques.").custom(false).build(),
                BackgroundEntity.builder().name("Urchin").description("You grew up on the streets alone, orphaned, and poor. You had no one to watch over you or to provide for you, so you learned to provide for yourself.").custom(false).build()
        );
    }

    // ==================== SUBRACE CREATION ====================
    private List<SubraceEntity> createSubraces() {
        List<SubraceEntity> subraces = new ArrayList<>();

        // Aasimar Subraces
        subraces.add(SubraceEntity.builder().name("Protector Aasimar").raceId(getRaceId("Aasimar")).build());
        subraces.add(SubraceEntity.builder().name("Scourge Aasimar").raceId(getRaceId("Aasimar")).build());
        subraces.add(SubraceEntity.builder().name("Fallen Aasimar").raceId(getRaceId("Aasimar")).build());

        // Dragonborn Subraces
        subraces.add(SubraceEntity.builder().name("Chromatic Dragonborn").raceId(getRaceId("Dragonborn")).build());
        subraces.add(SubraceEntity.builder().name("Metallic Dragonborn").raceId(getRaceId("Dragonborn")).build());
        subraces.add(SubraceEntity.builder().name("Gem Dragonborn").raceId(getRaceId("Dragonborn")).build());

        // Dwarf Subraces
        subraces.add(SubraceEntity.builder().name("Hill Dwarf").raceId(getRaceId("Dwarf")).build());
        subraces.add(SubraceEntity.builder().name("Mountain Dwarf").raceId(getRaceId("Dwarf")).build());
        subraces.add(SubraceEntity.builder().name("Duergar").raceId(getRaceId("Dwarf")).build());

        // Elf Subraces
        subraces.add(SubraceEntity.builder().name("High Elf").raceId(getRaceId("Elf")).build());
        subraces.add(SubraceEntity.builder().name("Wood Elf").raceId(getRaceId("Elf")).build());
        subraces.add(SubraceEntity.builder().name("Dark Elf (Drow)").raceId(getRaceId("Elf")).build());
        subraces.add(SubraceEntity.builder().name("Eladrin").raceId(getRaceId("Elf")).build());
        subraces.add(SubraceEntity.builder().name("Sea Elf").raceId(getRaceId("Elf")).build());
        subraces.add(SubraceEntity.builder().name("Shadar-kai").raceId(getRaceId("Elf")).build());

        // Gnome Subraces
        subraces.add(SubraceEntity.builder().name("Forest Gnome").raceId(getRaceId("Gnome")).build());
        subraces.add(SubraceEntity.builder().name("Rock Gnome").raceId(getRaceId("Gnome")).build());
        subraces.add(SubraceEntity.builder().name("Deep Gnome (Svirfneblin)").raceId(getRaceId("Gnome")).build());

        // Halfling Subraces
        subraces.add(SubraceEntity.builder().name("Lightfoot Halfling").raceId(getRaceId("Halfling")).build());
        subraces.add(SubraceEntity.builder().name("Stout Halfling").raceId(getRaceId("Halfling")).build());
        subraces.add(SubraceEntity.builder().name("Ghostwise Halfling").raceId(getRaceId("Halfling")).build());

        // Tiefling Subraces
        subraces.add(SubraceEntity.builder().name("Asmodeus Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Baalzebul Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Dispater Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Fierna Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Glasya Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Levistus Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Mammon Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Mephistopheles Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Zariel Tiefling").raceId(getRaceId("Tiefling")).build());
        subraces.add(SubraceEntity.builder().name("Feral Tiefling").raceId(getRaceId("Tiefling")).build());

        return subraces;
    }

    private Mono<Void> seedSpells() {
        log.info("Seeding spells...");
        List<SpellEntity> spells = createSpells();
        return spellRepository.saveAll(spells)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} spells", spells.size()));
    }

    // ==================== SPELL CREATION ====================
    private List<SpellEntity> createSpells() {
        List<SpellEntity> spells = new ArrayList<>();
        spells.addAll(createCantrips());
        spells.addAll(createLevel1Spells());
        spells.addAll(createLevel2Spells());
        spells.addAll(createLevel3Spells());
        spells.addAll(createLevel4Spells());
        spells.addAll(createLevel5Spells());
        spells.addAll(createLevel6Spells());
        spells.addAll(createLevel7Spells());
        spells.addAll(createLevel8Spells());
        spells.addAll(createLevel9Spells());
        return spells;
    }

    private List<SpellEntity> createCantrips() {
        return List.of(
            SpellEntity.builder().name("Acid Splash").description("You hurl a bubble of acid.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.ACID)).build(),
            SpellEntity.builder().name("Blade Ward").description("You extend your hand and trace a sigil of warding in the air.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Booming Blade").description("You brandish the weapon used in the spell's casting and make a melee attack.").minSlotLevel(0).castingTime(1).range(5).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.THUNDER)).build(),
            SpellEntity.builder().name("Chill Touch").description("You create a ghostly, skeletal hand in the space of a creature within range.").minSlotLevel(0).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Control Flames").description("You choose nonmagical flame that you can see within range.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Create Bonfire").description("You create a bonfire on ground that you can see within range.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Dancing Lights").description("You create up to four torch-sized lights within range.").minSlotLevel(0).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Druidcraft").description("Whispering to the spirits of nature, you create one of several minor effects.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Eldritch Blast").description("A beam of crackling energy streaks toward a creature within range.").minSlotLevel(0).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Fire Bolt").description("You hurl a mote of fire at a creature or object within range.").minSlotLevel(0).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Friends").description("For the duration, you have advantage on all Charisma checks directed at one creature.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Frostbite").description("You cause numbing frost to form on one creature that you can see within range.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Green-Flame Blade").description("You brandish the weapon used in the spell's casting and make a melee attack.").minSlotLevel(0).castingTime(1).range(5).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Guidance").description("You touch one willing creature. Once before the spell ends, the target can roll a d4.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Gust").description("You seize the air and compel it to create one of several effects.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Infestation").description("You cause a cloud of mites, fleas, and other parasites to appear.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.POISON)).build(),
            SpellEntity.builder().name("Light").description("You touch one object that is no larger than 10 feet in any dimension.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Lightning Lure").description("You create a lash of lightning energy that strikes at one creature of your choice.").minSlotLevel(0).castingTime(1).range(15).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.LIGHTNING)).build(),
            SpellEntity.builder().name("Mage Hand").description("A spectral, floating hand appears at a point you choose within range.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mending").description("This spell repairs a single break or tear in an object you touch.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Message").description("You point your finger toward a creature within range and whisper a message.").minSlotLevel(0).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mind Sliver").description("You drive a disorienting spike of psychic energy into the mind of one creature.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Minor Illusion").description("You create a sound or an image of an object within range.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mold Earth").description("You choose a portion of dirt or stone that you can see within range.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Poison Spray").description("You extend your hand toward a creature you can see within range.").minSlotLevel(0).castingTime(1).range(10).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.POISON)).build(),
            SpellEntity.builder().name("Prestidigitation").description("This spell is a minor magical trick that novice spellcasters use for practice.").minSlotLevel(0).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Primal Savagery").description("You channel primal magic to cause your teeth or fingernails to sharpen.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.ACID)).build(),
            SpellEntity.builder().name("Produce Flame").description("A flickering flame appears in your hand.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Ray of Frost").description("A frigid beam of blue-white light streaks toward a creature within range.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Resistance").description("You touch one willing creature. Once before the spell ends, the target can roll a d4.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Sacred Flame").description("Flame-like radiance descends on a creature that you can see within range.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.RADIANT)).build(),
            SpellEntity.builder().name("Shape Water").description("You choose an area of water that you can see within range.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Shillelagh").description("The wood of a club or quarterstaff you are holding is imbued with nature's power.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Shocking Grasp").description("Lightning springs from your hand to deliver a shock to a creature you try to touch.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.LIGHTNING)).build(),
            SpellEntity.builder().name("Spare the Dying").description("You touch a living creature that has 0 hit points.").minSlotLevel(0).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Sword Burst").description("You create a momentary circle of spectral blades that sweep around you.").minSlotLevel(0).castingTime(1).range(5).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Thaumaturgy").description("You manifest a minor wonder, a sign of supernatural power.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Thorn Whip").description("You create a long, vine-like whip covered in thorns.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.PIERCING)).build(),
            SpellEntity.builder().name("Thunderclap").description("You create a burst of thunderous sound that can be heard up to 100 feet away.").minSlotLevel(0).castingTime(1).range(5).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.THUNDER)).build(),
            SpellEntity.builder().name("Toll the Dead").description("You point at one creature you can see within range, and the sound of a dolorous bell fills the air.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("True Strike").description("You extend your hand and point a finger at a target in range.").minSlotLevel(0).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Vicious Mockery").description("You unleash a string of insults laced with subtle enchantments.").minSlotLevel(0).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Word of Radiance").description("You utter a divine word, and burning radiance erupts from you.").minSlotLevel(0).castingTime(1).range(5).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.RADIANT)).build()
        );
    }

    private List<SpellEntity> createLevel1Spells() {
        return List.of(
            SpellEntity.builder().name("Absorb Elements").description("Capture incoming energy to use as a melee attack boost.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Alarm").description("Set a mental or audible alarm against intrusion.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Animal Friendship").description("Convince a beast you mean it no harm.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Armor of Agathys").description("Protective frost that damages melee attackers.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Arms of Hadar").description("Tendrils of dark energy erupt from you.").minSlotLevel(1).castingTime(1).range(10).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Bane").description("Up to three creatures must make Charisma saves or subtract d4 from attacks and saves.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Bless").description("Up to three creatures add d4 to attack rolls and saving throws.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Burning Hands").description("A thin sheet of flames shoots forth from your outstretched fingertips.").minSlotLevel(1).castingTime(1).range(15).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Catapult").description("Choose one object and hurl it in a straight line.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Cause Fear").description("You awaken the sense of mortality in one creature.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Charm Person").description("You attempt to charm a humanoid you can see.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Chromatic Orb").description("You hurl a 4-inch-diameter sphere of energy at a creature.").minSlotLevel(1).castingTime(1).range(90).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.ACID, DamageTypeEnum.COLD, DamageTypeEnum.FIRE, DamageTypeEnum.LIGHTNING, DamageTypeEnum.POISON, DamageTypeEnum.THUNDER)).build(),
            SpellEntity.builder().name("Color Spray").description("A dazzling array of flashing, colored light springs from your hand.").minSlotLevel(1).castingTime(1).range(15).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Command").description("You speak a one-word command to a creature you can see.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Compelled Duel").description("You attempt to compel a creature into a duel.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Comprehend Languages").description("You understand the literal meaning of any spoken language.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Create or Destroy Water").description("You either create or destroy water.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Cure Wounds").description("A creature you touch regains hit points.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Detect Evil and Good").description("You sense the presence of aberrations, celestials, and more.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Detect Magic").description("You sense the presence of magic within 30 feet.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Detect Poison and Disease").description("You sense the presence and location of poisons and diseases.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Disguise Self").description("You make yourself look different.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Dissonant Whispers").description("You whisper a discordant melody only one creature can hear.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Divine Favor").description("Your weapon is imbued with divine power.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Earth Tremor").description("You cause a tremor in the ground within range.").minSlotLevel(1).castingTime(1).range(10).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Ensnaring Strike").description("The next time you hit a creature with a weapon attack, a writhing mass of vines appears.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Entangle").description("Grasping weeds and vines sprout from the ground.").minSlotLevel(1).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Expeditious Retreat").description("You can Dash as a bonus action on each of your turns.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Faerie Fire").description("Objects and creatures in a 20-foot cube are outlined in light.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("False Life").description("You gain 1d4 + 4 temporary hit points.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Feather Fall").description("Choose up to five falling creatures within range.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Find Familiar").description("You gain the service of a familiar.").minSlotLevel(1).castingTime(60).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Fog Cloud").description("You create a 20-foot-radius sphere of fog centered on a point.").minSlotLevel(1).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Goodberry").description("Up to ten berries appear in your hand, each restoring 1 hit point.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Grease").description("Slick grease covers the ground in a 10-foot square.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Guiding Bolt").description("A flash of light streaks toward a creature of your choice.").minSlotLevel(1).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.RADIANT)).build(),
            SpellEntity.builder().name("Hail of Thorns").description("The next time you hit a creature with a ranged weapon attack, thorns sprout.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Healing Word").description("A creature of your choice that you can see within range regains hit points.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Hellish Rebuke").description("You point your finger at the creature that damaged you.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Heroism").description("A willing creature you touch is imbued with bravery.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Hex").description("You place a curse on a creature that you can see within range.").minSlotLevel(1).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Hunter's Mark").description("You choose a creature you can see and mark it as your quarry.").minSlotLevel(1).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Ice Knife").description("You create a shard of ice and fling it at one creature.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PIERCING, DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Identify").description("You choose one object that you must touch throughout the casting.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Illusory Script").description("You write on parchment, paper, or some other suitable writing material.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Inflict Wounds").description("Make a melee spell attack against a creature you can reach.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Jump").description("You touch a creature. The creature's jump distance is tripled.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Longstrider").description("You touch a creature. The target's speed increases by 10 feet.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mage Armor").description("You touch a willing creature who isn't wearing armor.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Magic Missile").description("You create three glowing darts of magical force.").minSlotLevel(1).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Protection from Evil and Good").description("One willing creature you touch is protected.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Purify Food and Drink").description("All nonmagical food and drink within a 5-foot-radius sphere is purified.").minSlotLevel(1).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Ray of Sickness").description("A ray of sickening greenish energy lashes out toward a creature.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.POISON)).build(),
            SpellEntity.builder().name("Sanctuary").description("You ward a creature within range against attack.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Searing Smite").description("The next time you hit a creature with a melee weapon attack, your weapon flares.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Shield").description("An invisible barrier of magical force appears and protects you.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Shield of Faith").description("A shimmering field appears and surrounds a creature of your choice.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Silent Image").description("You create the image of an object, a creature, or some other visible phenomenon.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Sleep").description("This spell sends creatures into a magical slumber.").minSlotLevel(1).castingTime(1).range(90).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Speak with Animals").description("You gain the ability to comprehend and verbally communicate with beasts.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Tasha's Hideous Laughter").description("A creature of your choice falls into fits of laughter.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Tenser's Floating Disk").description("This spell creates a circular, horizontal plane of force.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Thunderous Smite").description("The first time you hit with a melee weapon attack, your weapon rings with thunder.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Thunderwave").description("A wave of thunderous force sweeps out from you.").minSlotLevel(1).castingTime(1).range(15).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.THUNDER)).build(),
            SpellEntity.builder().name("Unseen Servant").description("This spell creates an invisible, mindless, shapeless force.").minSlotLevel(1).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Witch Bolt").description("A beam of crackling, blue energy lances out toward a creature.").minSlotLevel(1).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wrathful Smite").description("The next time you hit with a melee weapon attack, your attack deals extra psychic damage.").minSlotLevel(1).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel2Spells() {
        return List.of(
            SpellEntity.builder().name("Acid Arrow").description("A shimmering green arrow streaks toward a target.").minSlotLevel(2).castingTime(1).range(90).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.ACID)).build(),
            SpellEntity.builder().name("Aganazzar's Scorcher").description("A line of roaring flame 30 feet long and 5 feet wide emanates from you.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Aid").description("Your spell bolsters your allies with toughness and resolve.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Alter Self").description("You assume a different form.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Animal Messenger").description("You use an animal to deliver a message.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Arcane Lock").description("You touch a closed door, window, gate, chest, or other entryway.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Augury").description("You receive an omen about the results of a specific course of action.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Barkskin").description("You touch a willing creature. Its skin has a rough, bark-like appearance.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Beast Sense").description("You touch a willing beast.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Blindness/Deafness").description("You can blind or deafen a foe.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Blur").description("Your body becomes blurred, shifting and wavering.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Branding Smite").description("The next time you hit a creature with a weapon attack, the weapon gleams.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Calm Emotions").description("You attempt to suppress strong emotions in a group of people.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Cloud of Daggers").description("You fill the air with spinning daggers in a cube 5 feet on each side.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Continual Flame").description("A flame springs from an object that you touch.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Cordon of Arrows").description("You plant four pieces of nonmagical ammunition in the ground.").minSlotLevel(2).castingTime(1).range(5).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Crown of Madness").description("One humanoid of your choice must succeed on a Wisdom saving throw.").minSlotLevel(2).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Darkness").description("Magical darkness spreads from a point you choose.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Darkvision").description("You touch a willing creature to grant it the ability to see in the dark.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Detect Thoughts").description("You can read the thoughts of certain creatures.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dragon's Breath").description("You touch one willing creature and imbue it with the power to spew magical energy.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dust Devil").description("Choose an unoccupied 5-foot cube of air that you can see.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Earthbind").description("Choose one creature you can see within range.").minSlotLevel(2).castingTime(1).range(300).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Enhance Ability").description("You touch a creature and bestow upon it a magical enhancement.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Enlarge/Reduce").description("You cause a creature or an object to grow larger or smaller.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Enthrall").description("You weave a distracting string of words.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Find Steed").description("You summon a spirit that assumes the form of a steed.").minSlotLevel(2).castingTime(10).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Find Traps").description("You sense the presence of any trap within range.").minSlotLevel(2).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Flame Blade").description("You evoke a fiery blade in your free hand.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Flaming Sphere").description("A 5-foot-diameter sphere of fire appears in an unoccupied space.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Gentle Repose").description("You touch a corpse or other remains.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Gust of Wind").description("A line of strong wind 60 feet long and 10 feet wide blasts from you.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Heat Metal").description("Choose a manufactured metal object that you can see.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Hold Person").description("Choose a humanoid that you can see within range.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Invisibility").description("A creature you touch becomes invisible.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Knock").description("Choose an object that you can see within range.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Lesser Restoration").description("You touch a creature and can end either one disease or one condition.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Levitate").description("One creature or loose object of your choice rises vertically.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Locate Animals or Plants").description("Describe or name a specific kind of beast or plant.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Locate Object").description("Describe or name an object that is familiar to you.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Magic Mouth").description("You implant a message within an object.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Magic Weapon").description("You touch a nonmagical weapon. It becomes a magic weapon.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Maximilian's Earthen Grasp").description("You choose a 5-foot-square unoccupied space.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mind Spike").description("You reach into the mind of one creature you can see.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mirror Image").description("Three illusory duplicates of yourself appear in your space.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Misty Step").description("You teleport up to 30 feet to an unoccupied space.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Moonbeam").description("A silvery beam of pale light shines down in a 5-foot-radius cylinder.").minSlotLevel(2).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Nystul's Magic Aura").description("You place an illusion on a creature or object you touch.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Pass Without Trace").description("A veil of shadows and silence radiates from you.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Phantasmal Force").description("You craft an illusion that takes root in the mind of a creature.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Prayer of Healing").description("Up to six creatures of your choice regain hit points.").minSlotLevel(2).castingTime(10).range(30).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Protection from Poison").description("You touch a creature. If it is poisoned, you neutralize the poison.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Pyrotechnics").description("Choose an area of nonmagical flame that you can see.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Ray of Enfeeblement").description("A black beam of enervating energy springs from your finger.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Rope Trick").description("You touch a length of rope that is up to 60 feet long.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Scorching Ray").description("You create three rays of fire and hurl them at targets.").minSlotLevel(2).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("See Invisibility").description("For the duration, you see invisible creatures and objects.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Shadow Blade").description("You weave together threads of shadow to create a sword of solidified gloom.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Shatter").description("A sudden loud ringing noise painfully intense erupts.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.THUNDER)).build(),
            SpellEntity.builder().name("Silence").description("For the duration, no sound can be created within or pass through a 20-foot-radius sphere.").minSlotLevel(2).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Skywrite").description("You cause up to ten words to form in a part of the sky you can see.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Snilloc's Snowball Swarm").description("A flurry of magic snowballs erupts from a point you choose.").minSlotLevel(2).castingTime(1).range(90).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Spider Climb").description("Until the spell ends, one willing creature you touch gains the ability to move up walls.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Spike Growth").description("The ground in a 20-foot radius centered on a point becomes difficult terrain.").minSlotLevel(2).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Spiritual Weapon").description("You create a floating, spectral weapon within range.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Suggestion").description("You suggest a course of activity to a creature.").minSlotLevel(2).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Warding Bond").description("This spell wards a willing creature you touch.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Warding Wind").description("A strong wind swirls around you in a 10-foot radius.").minSlotLevel(2).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Web").description("You conjure a mass of thick, sticky webbing.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Zone of Truth").description("You create a magical zone that guards against deception.").minSlotLevel(2).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build()
        );
    }

    private List<SpellEntity> createLevel3Spells() {
        return List.of(
            SpellEntity.builder().name("Animate Dead").description("This spell creates an undead servant.").minSlotLevel(3).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Aura of Vitality").description("Healing energy radiates from you in an aura.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Beacon of Hope").description("This spell bestows hope and vitality.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Bestow Curse").description("You touch a creature, and that creature must succeed on a Wisdom saving throw.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Blinding Smite").description("The next time you hit a creature with a melee weapon attack, your weapon flares.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Blink").description("Roll a d20 at the end of each of your turns.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Call Lightning").description("A storm cloud appears in the shape of a cylinder.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Clairvoyance").description("You create an invisible sensor within range.").minSlotLevel(3).castingTime(10).range(1).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Conjure Animals").description("You summon fey spirits that take the form of beasts.").minSlotLevel(3).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Conjure Barrage").description("You throw a nonmagical weapon or fire a piece of nonmagical ammunition.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.SLASHING)).build(),
            SpellEntity.builder().name("Counterspell").description("You attempt to interrupt a creature in the process of casting a spell.").minSlotLevel(3).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Create Food and Water").description("You create 45 pounds of food and 30 gallons of water.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Crusader's Mantle").description("Holy power radiates from you in an aura.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Daylight").description("A 60-foot-radius sphere of light spreads out from a point you choose.").minSlotLevel(3).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Dispel Magic").description("Choose one creature, object, or magical effect within range.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Elemental Weapon").description("A nonmagical weapon you touch becomes a magic weapon.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Enemies Abound").description("You reach into the mind of one creature you can see.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Erupting Earth").description("Choose a point you can see on the ground within range.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Fear").description("You project a phantasmal image of a creature's worst fears.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Feign Death").description("You touch a willing creature and put it into a cataleptic state.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Fireball").description("A bright streak flashes from your pointing finger to a point you choose.").minSlotLevel(3).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Flame Arrows").description("You touch a quiver containing arrows or bolts.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Fly").description("You touch a willing creature. The target gains a flying speed of 60 feet.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Gaseous Form").description("You transform a willing creature you touch into a misty cloud.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Glyph of Warding").description("You inscribe a glyph that harms other creatures.").minSlotLevel(3).castingTime(60).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Haste").description("Choose a willing creature that you can see within range.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Hunger of Hadar").description("You open a gateway to the dark between the stars.").minSlotLevel(3).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Hypnotic Pattern").description("You create a twisting pattern of colors that weaves through the air.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Leomund's Tiny Hut").description("A 10-foot-radius immobile dome of force springs into existence.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Lightning Bolt").description("A stroke of lightning forming a line 100 feet long and 5 feet wide.").minSlotLevel(3).castingTime(1).range(100).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.LIGHTNING)).build(),
            SpellEntity.builder().name("Magic Circle").description("You create a 10-foot-radius, 20-foot-tall cylinder of magical energy.").minSlotLevel(3).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Major Image").description("You create the image of an object, a creature, or some other visible phenomenon.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mass Healing Word").description("As you call out words of restoration, up to six creatures regain hit points.").minSlotLevel(3).castingTime(1).range(60).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Meld into Stone").description("You step into a stone object or surface large enough to contain your body.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Melf's Minute Meteors").description("You create six tiny meteors in your space.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Nondetection").description("You hide a target from divination magic.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Phantom Steed").description("A Large quasi-real, horselike creature appears on the ground.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Plant Growth").description("This spell channels vitality into plants within a specific area.").minSlotLevel(3).castingTime(1).range(150).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Protection from Energy").description("A willing creature you touch gains resistance to one damage type.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Remove Curse").description("At your touch, all curses affecting one creature or object end.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Revivify").description("You touch a creature that has died within the last minute.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Sending").description("You send a short message of twenty-five words or less.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Sleet Storm").description("Freezing rain and sleet fall in a 20-foot-tall cylinder.").minSlotLevel(3).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Slow").description("You alter time around up to six creatures of your choice.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Speak with Dead").description("You grant the semblance of life to a corpse of your choice.").minSlotLevel(3).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Speak with Plants").description("You imbue plants within 30 feet of you with limited sentience.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Spirit Guardians").description("You call forth spirits to protect you.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Spirit Shroud").description("You call forth spirits of the dead.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Stinking Cloud").description("You create a 20-foot-radius sphere of yellow, nauseating gas.").minSlotLevel(3).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Fey").description("You call forth a fey spirit.").minSlotLevel(3).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Shadowspawn").description("You call forth a shadowy spirit.").minSlotLevel(3).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Undead").description("You call forth an undead spirit.").minSlotLevel(3).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Tidal Wave").description("You conjure up a wave of water that crashes down.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Tongues").description("This spell grants the creature you touch the ability to understand any spoken language.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Vampiric Touch").description("The touch of your shadow-wreathed hand can siphon life force.").minSlotLevel(3).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Sand").description("You conjure up a wall of swirling sand on the ground.").minSlotLevel(3).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Water").description("You conjure up a wall of water on the ground.").minSlotLevel(3).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Water Breathing").description("This spell grants up to ten willing creatures the ability to breathe underwater.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Water Walk").description("This spell grants the ability to move across any liquid surface.").minSlotLevel(3).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Wind Wall").description("A wall of strong wind rises from the ground.").minSlotLevel(3).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel4Spells() {
        return List.of(
            SpellEntity.builder().name("Arcane Eye").description("You create an invisible, magical eye within range.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Aura of Life").description("Life-preserving energy radiates from you in an aura.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Aura of Purity").description("Purifying energy radiates from you in an aura.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Banishment").description("You attempt to send one creature to another plane of existence.").minSlotLevel(4).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Blight").description("Necromantic energy washes over a creature of your choice.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Charm Monster").description("You attempt to charm a creature you can see.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Compulsion").description("Creatures of your choice must make a Wisdom saving throw.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Confusion").description("Each creature in a 10-foot-radius sphere must succeed on a Wisdom save.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Conjure Minor Elementals").description("You summon elementals that appear in unoccupied spaces.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Conjure Woodland Beings").description("You summon fey creatures that appear in unoccupied spaces.").minSlotLevel(4).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Control Water").description("You control any freestanding water inside an area you choose.").minSlotLevel(4).castingTime(1).range(300).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Death Ward").description("You touch a creature and grant it a measure of protection from death.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Dimension Door").description("You teleport yourself to any spot within range.").minSlotLevel(4).castingTime(1).range(500).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Divination").description("Your magic and an offering put you in contact with a god or a god's servants.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Dominate Beast").description("You attempt to beguile a beast that you can see.").minSlotLevel(4).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Elemental Bane").description("Choose one creature you can see and one damage type.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Evard's Black Tentacles").description("Squirming, ebony tentacles fill a 20-foot square on ground.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Fabricate").description("You convert raw materials into products of the same material.").minSlotLevel(4).castingTime(10).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Find Greater Steed").description("You summon a spirit that assumes the form of a loyal mount.").minSlotLevel(4).castingTime(10).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Fire Shield").description("Thin and wispy flames wreathe your body for the duration.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Freedom of Movement").description("You touch a willing creature. The target's movement is unaffected.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Giant Insect").description("You transform up to ten centipedes, spiders, wasps, or scorpions.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Grasping Vine").description("You conjure a vine that sprouts from the ground.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Greater Invisibility").description("You or a creature you touch becomes invisible.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Guardian of Faith").description("A Large spectral guardian appears and hovers for the duration.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Hallucinatory Terrain").description("You make natural terrain look, sound, and smell like another terrain.").minSlotLevel(4).castingTime(10).range(300).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Ice Storm").description("A hail of rock-hard ice pounds to the ground.").minSlotLevel(4).castingTime(1).range(300).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING, DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Leomund's Secret Chest").description("You hide a chest, and all its contents, on the Ethereal Plane.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Locate Creature").description("Describe or name a creature that is familiar to you.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mordenkainen's Faithful Hound").description("You conjure a phantom watchdog in an unoccupied space.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mordenkainen's Private Sanctum").description("You make an area within range magically secure.").minSlotLevel(4).castingTime(10).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Otiluke's Resilient Sphere").description("A sphere of shimmering force encloses a creature or object.").minSlotLevel(4).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Phantasmal Killer").description("You tap into the nightmares of a creature.").minSlotLevel(4).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Polymorph").description("This spell transforms a creature into a new form.").minSlotLevel(4).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Raulothim's Psychic Lance").description("You unleash a shimmering lance of psychic power.").minSlotLevel(4).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Shadow of Moil").description("Flame-like shadows wreathe your body.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Sickening Radiance").description("Dim, greenish light spreads within a 30-foot-radius sphere.").minSlotLevel(4).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Staggering Smite").description("The next time you hit a creature with a melee weapon attack.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Stone Shape").description("You touch a stone object of Medium size or smaller.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Stoneskin").description("This spell turns the flesh of a willing creature as hard as stone.").minSlotLevel(4).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Storm Sphere").description("A 20-foot-radius sphere of whirling air springs into existence.").minSlotLevel(4).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Aberration").description("You call forth an aberrant spirit.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Construct").description("You call forth the spirit of a construct.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Elemental").description("You call forth an elemental spirit.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Vitriolic Sphere").description("You point at a location within range, and a glowing orb of acid appears.").minSlotLevel(4).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.ACID)).build(),
            SpellEntity.builder().name("Wall of Fire").description("You create a wall of fire on a solid surface within range.").minSlotLevel(4).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Watery Sphere").description("You conjure up a sphere of water with a 5-foot radius.").minSlotLevel(4).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel5Spells() {
        return List.of(
            SpellEntity.builder().name("Animate Objects").description("Objects come to life at your command.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Antilife Shell").description("A shimmering barrier extends out from you in a 10-foot radius.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Awaken").description("You spend the casting time tracing magical pathways within a precious gemstone.").minSlotLevel(5).castingTime(480).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Banishing Smite").description("The next time you hit a creature with a weapon attack.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Bigby's Hand").description("You create a Large hand of shimmering, translucent force.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Circle of Power").description("Divine energy radiates from you, distorting and diffusing magical energy.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Cloudkill").description("You create a 20-foot-radius sphere of poisonous, yellow-green fog.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Commune").description("You contact your deity or a divine proxy and ask up to three questions.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Commune with Nature").description("You briefly become one with nature.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Cone of Cold").description("A blast of cold air erupts from your hands.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Conjure Elemental").description("You call forth an elemental servant.").minSlotLevel(5).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Conjure Volley").description("You fire a piece of nonmagical ammunition.").minSlotLevel(5).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Contact Other Plane").description("You mentally contact a demigod or the spirit of a long-dead sage.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Contagion").description("Your touch inflicts disease.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Control Winds").description("You take control of the air in a 100-foot cube.").minSlotLevel(5).castingTime(1).range(300).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Creation").description("You pull wisps of shadow material to create a nonliving object.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Danse Macabre").description("Threads of dark power leap from your fingers to pierce up to five corpses.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dawn").description("The light of dawn shines down on a location you specify.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Destructive Wave").description("You strike the ground, creating a burst of divine energy.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.THUNDER, DamageTypeEnum.RADIANT)).build(),
            SpellEntity.builder().name("Dispel Evil and Good").description("Shimmering energy surrounds and protects you.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dominate Person").description("You attempt to beguile a humanoid that you can see.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dream").description("This spell shapes a creature's dreams.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Enervation").description("A tendril of inky darkness reaches out from you.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Far Step").description("You teleport up to 60 feet to an unoccupied space you can see.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Flame Strike").description("A vertical column of divine fire roars down from the heavens.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE, DamageTypeEnum.RADIANT)).build(),
            SpellEntity.builder().name("Geas").description("You place a magical command on a creature.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Greater Restoration").description("You imbue a creature you touch with positive energy.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Hallow").description("You touch a point and infuse an area around it with holy or unholy power.").minSlotLevel(5).castingTime(1440).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Hold Monster").description("Choose a creature that you can see within range.").minSlotLevel(5).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Holy Weapon").description("You imbue a weapon you touch with holy power.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Immolation").description("Flames wreathe one creature you can see within range.").minSlotLevel(5).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Infernal Calling").description("Uttering a dark incantation, you summon a devil from the Nine Hells.").minSlotLevel(5).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Insect Plague").description("Swarming, biting locusts fill a 20-foot-radius sphere.").minSlotLevel(5).castingTime(1).range(300).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Legend Lore").description("Name or describe a person, place, or object.").minSlotLevel(5).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Maelstrom").description("A mass of 5-foot-deep water appears and swirls in a 30-foot radius.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mass Cure Wounds").description("A wave of healing energy washes out from a point of your choice.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Mislead").description("You become invisible at the same time that an illusory double appears.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Modify Memory").description("You attempt to reshape another creature's memories.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Negative Energy Flood").description("You send ribbons of negative energy at one creature.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Passwall").description("A passage appears at a point of your choice.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Planar Binding").description("You attempt to bind a celestial, elemental, fey, or fiend.").minSlotLevel(5).castingTime(60).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Raise Dead").description("You return a dead creature you touch to life.").minSlotLevel(5).castingTime(60).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Rary's Telepathic Bond").description("You forge a telepathic link among up to eight willing creatures.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Reincarnate").description("You touch a dead humanoid or a piece of a dead humanoid.").minSlotLevel(5).castingTime(60).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Scrying").description("You can see and hear a particular creature you choose.").minSlotLevel(5).castingTime(10).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Seeming").description("This spell allows you to change the appearance of any number of creatures.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Skill Empowerment").description("Your magic deepens a creature's understanding of its own talent.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Steel Wind Strike").description("You flourish the weapon used in the casting and then vanish.").minSlotLevel(5).castingTime(1).range(30).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.MELEE).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Summon Celestial").description("You call forth a celestial spirit.").minSlotLevel(5).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Summon Draconic Spirit").description("You call forth a draconic spirit.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Swift Quiver").description("You transmute your quiver so it produces an endless supply of ammunition.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Synaptic Static").description("You choose a point within range and cause psychic energy to explode.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Telekinesis").description("You gain the ability to move or manipulate creatures or objects by thought.").minSlotLevel(5).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Teleportation Circle").description("As you cast the spell, you draw a 10-foot-diameter circle.").minSlotLevel(5).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Transmute Rock").description("You choose an area of stone or mud that you can see.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Tree Stride").description("You gain the ability to enter a tree and move from inside it.").minSlotLevel(5).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Force").description("An invisible wall of force springs into existence.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Light").description("A shimmering wall of bright light appears.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Stone").description("A nonmagical wall of solid stone springs into existence.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wrath of Nature").description("You call out to the spirits of nature to rouse them against your enemies.").minSlotLevel(5).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel6Spells() {
        return List.of(
            SpellEntity.builder().name("Arcane Gate").description("You create linked teleportation portals.").minSlotLevel(6).castingTime(1).range(500).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Blade Barrier").description("You create a vertical wall of whirling, razor-sharp blades.").minSlotLevel(6).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Bones of the Earth").description("You cause up to six pillars of stone to burst from places on the ground.").minSlotLevel(6).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Chain Lightning").description("You create a bolt of lightning that arcs toward a target.").minSlotLevel(6).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.LIGHTNING)).build(),
            SpellEntity.builder().name("Circle of Death").description("A sphere of negative energy ripples out in a 60-foot-radius sphere.").minSlotLevel(6).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Conjure Fey").description("You summon a fey creature of challenge rating 6 or lower.").minSlotLevel(6).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Contingency").description("Choose a spell of 5th level or lower that you can cast.").minSlotLevel(6).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Create Homunculus").description("You create a homunculus creature while speaking an intricate incantation.").minSlotLevel(6).castingTime(60).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Create Undead").description("You can cast this spell only at night.").minSlotLevel(6).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Disintegrate").description("A thin green ray springs from your pointing finger.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FORCE)).build(),
            SpellEntity.builder().name("Drawmij's Instant Summons").description("You touch an object weighing 10 pounds or less.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Druid Grove").description("You invoke the spirits of nature to protect an area outdoors or underground.").minSlotLevel(6).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Eyebite").description("For the spell's duration, your eyes become an inky void.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Find the Path").description("This spell allows you to find the shortest, most direct physical route.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Flesh to Stone").description("You attempt to turn one creature into stone.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Forbiddance").description("You create a ward against magical travel.").minSlotLevel(6).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Globe of Invulnerability").description("An immobile, faintly shimmering barrier springs into existence.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Guards and Wards").description("You create a ward that protects up to 2,500 square feet of floor space.").minSlotLevel(6).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Harm").description("You unleash a virulent disease on a creature.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Heal").description("Choose a creature that you can see within range.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Heroes' Feast").description("You bring forth a great feast, including magnificent food and drink.").minSlotLevel(6).castingTime(10).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Investiture of Flame").description("Flames race across your body, shedding bright light.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Investiture of Ice").description("Until the spell ends, ice rimes your body.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Investiture of Stone").description("Until the spell ends, bits of rock spread across your body.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Investiture of Wind").description("Until the spell ends, wind whirls around you.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Magic Jar").description("Your body falls into a catatonic state as your soul leaves it.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mass Suggestion").description("You suggest a course of activity to up to twelve creatures.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mental Prison").description("You attempt to bind a creature within an illusory cell.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Move Earth").description("Choose an area of terrain no larger than 40 feet on a side.").minSlotLevel(6).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Otiluke's Freezing Sphere").description("A frigid globe of cold energy streaks from your fingertips.").minSlotLevel(6).castingTime(1).range(300).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Otto's Irresistible Dance").description("Choose one creature that you can see within range.").minSlotLevel(6).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Planar Ally").description("You beseech an otherworldly entity for aid.").minSlotLevel(6).castingTime(10).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Primordial Ward").description("You have resistance to acid, cold, fire, lightning, and thunder damage.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Programmed Illusion").description("You create an illusion of an object, a creature, or some other visible phenomenon.").minSlotLevel(6).castingTime(1).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Scatter").description("The air quivers around up to five creatures of your choice.").minSlotLevel(6).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Soul Cage").description("This spell snatches the soul of a humanoid as it dies.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Summon Fiend").description("You call forth a fiendish spirit.").minSlotLevel(6).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Sunbeam").description("A beam of brilliant light flashes out from your hand.").minSlotLevel(6).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Tasha's Otherworldly Guise").description("Uttering an incantation, you draw on the magic of the Lower or Upper Planes.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Transport via Plants").description("This spell creates a magical link between a Large or larger plant.").minSlotLevel(6).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("True Seeing").description("This spell gives the willing creature you touch the ability to see things.").minSlotLevel(6).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Wall of Ice").description("You create a wall of ice on a solid surface within range.").minSlotLevel(6).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wall of Thorns").description("You create a wall of tough, pliable, tangled brush bristling with thorns.").minSlotLevel(6).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wind Walk").description("You and up to ten willing creatures take on a gaseous form.").minSlotLevel(6).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Word of Recall").description("You and up to five willing creatures within 5 feet of you instantly teleport.").minSlotLevel(6).castingTime(1).range(5).spellType(SpellTypeEnum.UTILITY).build()
        );
    }

    private List<SpellEntity> createLevel7Spells() {
        return List.of(
            SpellEntity.builder().name("Conjure Celestial").description("You summon a celestial of challenge rating 4 or lower.").minSlotLevel(7).castingTime(1).range(90).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Crown of Stars").description("Seven star-like motes of light appear and orbit your head.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Delayed Blast Fireball").description("A beam of yellow light flashes from your pointing finger.").minSlotLevel(7).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Divine Word").description("You utter a divine word, imbued with the power that shaped the world.").minSlotLevel(7).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Draconic Transformation").description("You take on a draconic form with huge spectral wings.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dream of the Blue Veil").description("You and up to eight willing creatures fall unconscious.").minSlotLevel(7).castingTime(10).range(20).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Etherealness").description("You step into the border regions of the Ethereal Plane.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Finger of Death").description("You send negative energy coursing through a creature.").minSlotLevel(7).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Fire Storm").description("A storm made up of sheets of roaring flame appears.").minSlotLevel(7).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE)).build(),
            SpellEntity.builder().name("Forcecage").description("An immobile, invisible, cube-shaped prison composed of magical force.").minSlotLevel(7).castingTime(1).range(100).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mirage Arcane").description("You make terrain in an area up to 1 mile square look, sound, smell like another terrain.").minSlotLevel(7).castingTime(10).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mordenkainen's Magnificent Mansion").description("You conjure an extradimensional dwelling.").minSlotLevel(7).castingTime(1).range(300).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mordenkainen's Sword").description("You create a sword-shaped plane of force that hovers within range.").minSlotLevel(7).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Plane Shift").description("You and up to eight willing creatures join hands in a circle.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Power Word Pain").description("You speak a word of power that causes waves of intense pain.").minSlotLevel(7).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Prismatic Spray").description("Eight multicolored rays of light flash from your hand.").minSlotLevel(7).castingTime(1).range(60).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.SELF).damageTypes(Set.of(DamageTypeEnum.FIRE, DamageTypeEnum.ACID, DamageTypeEnum.LIGHTNING, DamageTypeEnum.POISON, DamageTypeEnum.COLD)).build(),
            SpellEntity.builder().name("Project Image").description("You create an illusory copy of yourself.").minSlotLevel(7).castingTime(1).range(500).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Regenerate").description("You touch a creature and stimulate its natural healing ability.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Resurrection").description("You touch a dead creature that has been dead for no more than a century.").minSlotLevel(7).castingTime(60).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Reverse Gravity").description("This spell reverses gravity in a 50-foot-radius cylinder.").minSlotLevel(7).castingTime(1).range(100).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Sequester").description("You cause a creature or an object to become hidden.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Simulacrum").description("You shape an illusory duplicate of one beast or humanoid.").minSlotLevel(7).castingTime(720).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Symbol").description("When you cast this spell, you inscribe a harmful glyph.").minSlotLevel(7).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Teleport").description("This spell instantly transports you and up to eight willing creatures.").minSlotLevel(7).castingTime(1).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Temple of the Gods").description("You cause a temple to shimmer into existence on ground you can see.").minSlotLevel(7).castingTime(60).range(120).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Whirlwind").description("A whirlwind howls down to a point that you can see on the ground.").minSlotLevel(7).castingTime(1).range(300).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel8Spells() {
        return List.of(
            SpellEntity.builder().name("Abi-Dalzim's Horrid Wilting").description("You draw the moisture from every creature in a 30-foot cube.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Animal Shapes").description("Your magic turns others into beasts.").minSlotLevel(8).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Antimagic Field").description("A 10-foot-radius invisible sphere of antimagic surrounds you.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Antipathy/Sympathy").description("This spell attracts or repels creatures of your choice.").minSlotLevel(8).castingTime(60).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Clone").description("This spell grows an inert duplicate of a living creature.").minSlotLevel(8).castingTime(60).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Control Weather").description("You take control of the weather within 5 miles of you.").minSlotLevel(8).castingTime(10).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Dark Star").description("A sphere of crushing darkness forms.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Demiplane").description("You create a shadowy door on a flat solid surface.").minSlotLevel(8).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Dominate Monster").description("You attempt to beguile a creature that you can see.").minSlotLevel(8).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Earthquake").description("You create a seismic disturbance at a point on the ground.").minSlotLevel(8).castingTime(1).range(500).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Feeblemind").description("You blast the mind of a creature, attempting to shatter its intellect.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Glibness").description("Until the spell ends, when you make a Charisma check, you can replace the roll with 15.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Holy Aura").description("Divine light washes out from you and coalesces in a soft radiance.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Illusory Dragon").description("By gathering threads of shadow material, you create a Huge shadowy dragon.").minSlotLevel(8).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Incendiary Cloud").description("A swirling cloud of smoke shot through with white-hot embers appears.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Maddening Darkness").description("Magical darkness spreads from a point you choose.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Maze").description("You banish a creature into a labyrinthine demiplane.").minSlotLevel(8).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mighty Fortress").description("A fortress of stone erupts from a square area of ground.").minSlotLevel(8).castingTime(1).range(1).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Mind Blank").description("Until the spell ends, one willing creature you touch is immune to psychic damage.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Power Word Stun").description("You speak a word of power that can overwhelm the mind of one creature.").minSlotLevel(8).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Reality Break").description("You shatter the barriers between realities and timelines.").minSlotLevel(8).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Sunburst").description("Brilliant sunlight flashes in a 60-foot radius centered on a point.").minSlotLevel(8).castingTime(1).range(150).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.RADIANT)).build(),
            SpellEntity.builder().name("Telepathy").description("You create a telepathic link between yourself and a willing creature.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Tsunami").description("A wall of water springs into existence at a point you choose.").minSlotLevel(8).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build()
        );
    }

    private List<SpellEntity> createLevel9Spells() {
        return List.of(
            SpellEntity.builder().name("Astral Projection").description("You and up to eight willing creatures project your astral bodies.").minSlotLevel(9).castingTime(60).range(10).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Blade of Disaster").description("You create a blade-shaped planar rift about 3 feet long.").minSlotLevel(9).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Foresight").description("You touch a willing creature and bestow a limited ability to see into the future.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Gate").description("You conjure a portal linking an unoccupied space you can see.").minSlotLevel(9).castingTime(1).range(60).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Imprisonment").description("You create a magical restraint to hold a creature.").minSlotLevel(9).castingTime(1).range(30).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Invulnerability").description("You are immune to all damage until the spell ends.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Mass Heal").description("A flood of healing energy flows from you into injured creatures around you.").minSlotLevel(9).castingTime(1).range(60).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Mass Polymorph").description("You transform up to ten creatures into new forms.").minSlotLevel(9).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Meteor Swarm").description("Blazing orbs of fire plummet to the ground at four different points.").minSlotLevel(9).castingTime(1).range(1).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.FIRE, DamageTypeEnum.BLUDGEONING)).build(),
            SpellEntity.builder().name("Power Word Heal").description("A wave of healing energy washes over a creature you touch.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Power Word Kill").description("You utter a word of power that can compel one creature to die instantly.").minSlotLevel(9).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Prismatic Wall").description("A shimmering, multicolored plane of light forms a vertical wall.").minSlotLevel(9).castingTime(1).range(60).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("Psychic Scream").description("You unleash the power of your mind to blast the intellect of up to ten creatures.").minSlotLevel(9).castingTime(1).range(90).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.PSYCHIC)).build(),
            SpellEntity.builder().name("Ravenous Void").description("You create a 20-foot-radius sphere of destructive gravitational force.").minSlotLevel(9).castingTime(1).range(1000).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Shapechange").description("You assume the form of a different creature for the duration.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Storm of Vengeance").description("A churning storm cloud forms, centered on a point you can see.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Time Ravage").description("You target a creature you can see within range.").minSlotLevel(9).castingTime(1).range(90).spellType(SpellTypeEnum.ATTACK).attackType(AttackTypeEnum.RANGED).damageTypes(Set.of(DamageTypeEnum.NECROTIC)).build(),
            SpellEntity.builder().name("Time Stop").description("You briefly stop the flow of time for everyone but yourself.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build(),
            SpellEntity.builder().name("True Polymorph").description("Choose one creature or nonmagical object that you can see.").minSlotLevel(9).castingTime(1).range(30).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("True Resurrection").description("You touch a creature that has been dead for no longer than 200 years.").minSlotLevel(9).castingTime(60).range(0).spellType(SpellTypeEnum.HEAL).build(),
            SpellEntity.builder().name("Weird").description("Drawing on the deepest fears of a group of creatures.").minSlotLevel(9).castingTime(1).range(120).spellType(SpellTypeEnum.CONCENTRATION).build(),
            SpellEntity.builder().name("Wish").description("Wish is the mightiest spell a mortal creature can cast.").minSlotLevel(9).castingTime(1).range(0).spellType(SpellTypeEnum.UTILITY).build()
        );
    }

    private Mono<Void> seedEquipment() {
        log.info("Seeding equipment...");
        List<EquipmentEntity> equipment = createEquipment();
        return equipmentRepository.saveAll(equipment)
                .then()
                .doOnSuccess(v -> log.info("Seeded {} equipment items", equipment.size()));
    }

    // ==================== EQUIPMENT CREATION ====================
    private List<EquipmentEntity> createEquipment() {
        List<EquipmentEntity> equipment = new ArrayList<>();
        // Basic mundane equipment
        equipment.addAll(createBasicArmor());
        equipment.addAll(createBasicWeapons());
        equipment.addAll(createPacks());
        equipment.addAll(createTools());
        equipment.addAll(createFoci());
        equipment.addAll(createMusicalInstruments());
        equipment.addAll(createAmmunition());
        // Magic items
        equipment.addAll(createMagicArmor());
        equipment.addAll(createMagicWeapons());
        equipment.addAll(createPotions());
        equipment.addAll(createRings());
        equipment.addAll(createRods());
        equipment.addAll(createScrolls());
        equipment.addAll(createStaffs());
        equipment.addAll(createWands());
        equipment.addAll(createWondrousItems());
        return equipment;
    }

    // ==================== BASIC MUNDANE EQUIPMENT ====================
    private List<EquipmentEntity> createBasicArmor() {
        return List.of(
            // Light Armor
            EquipmentEntity.builder().name("Padded Armor").description("Light armor. AC 11 + Dex modifier. Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 5)).weight(8).build(),
            EquipmentEntity.builder().name("Leather Armor").description("Light armor. AC 11 + Dex modifier.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 10)).weight(10).build(),
            EquipmentEntity.builder().name("Studded Leather Armor").description("Light armor. AC 12 + Dex modifier.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 45)).weight(13).build(),
            // Medium Armor
            EquipmentEntity.builder().name("Hide Armor").description("Medium armor. AC 12 + Dex modifier (max 2).").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 10)).weight(12).build(),
            EquipmentEntity.builder().name("Chain Shirt").description("Medium armor. AC 13 + Dex modifier (max 2).").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 50)).weight(20).build(),
            EquipmentEntity.builder().name("Scale Mail").description("Medium armor. AC 14 + Dex modifier (max 2). Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 50)).weight(45).build(),
            EquipmentEntity.builder().name("Breastplate").description("Medium armor. AC 14 + Dex modifier (max 2).").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 400)).weight(20).build(),
            EquipmentEntity.builder().name("Half Plate").description("Medium armor. AC 15 + Dex modifier (max 2). Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 750)).weight(40).build(),
            // Heavy Armor
            EquipmentEntity.builder().name("Ring Mail").description("Heavy armor. AC 14. Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 30)).weight(40).build(),
            EquipmentEntity.builder().name("Chain Mail").description("Heavy armor. AC 16. Str 13 required. Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 75)).weight(55).build(),
            EquipmentEntity.builder().name("Splint Armor").description("Heavy armor. AC 17. Str 15 required. Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 200)).weight(60).build(),
            EquipmentEntity.builder().name("Plate Armor").description("Heavy armor. AC 18. Str 15 required. Disadvantage on Stealth checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 1500)).weight(65).build(),
            // Shields
            EquipmentEntity.builder().name("Shield").description("+2 bonus to AC.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 10)).weight(6).build(),
            EquipmentEntity.builder().name("Wooden Shield").description("+2 bonus to AC. Made of wood, suitable for druids.").equipmentCategory(EquipmentCategoryEnum.ARMOR).price(Map.of(CoinEnum.GP, 10)).weight(6).build()
        );
    }

    private List<EquipmentEntity> createBasicWeapons() {
        return List.of(
            // Simple Melee Weapons
            EquipmentEntity.builder().name("Club").description("Simple melee weapon. 1d4 bludgeoning. Light.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.SP, 1)).weight(2).build(),
            EquipmentEntity.builder().name("Dagger").description("Simple melee weapon. 1d4 piercing. Finesse, light, thrown (20/60).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 2)).weight(1).build(),
            EquipmentEntity.builder().name("Greatclub").description("Simple melee weapon. 1d8 bludgeoning. Two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.SP, 2)).weight(10).build(),
            EquipmentEntity.builder().name("Handaxe").description("Simple melee weapon. 1d6 slashing. Light, thrown (20/60).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 5)).weight(2).build(),
            EquipmentEntity.builder().name("Javelin").description("Simple melee weapon. 1d6 piercing. Thrown (30/120).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.SP, 5)).weight(2).build(),
            EquipmentEntity.builder().name("Light Hammer").description("Simple melee weapon. 1d4 bludgeoning. Light, thrown (20/60).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 2)).weight(2).build(),
            EquipmentEntity.builder().name("Mace").description("Simple melee weapon. 1d6 bludgeoning.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 5)).weight(4).build(),
            EquipmentEntity.builder().name("Quarterstaff").description("Simple melee weapon. 1d6 bludgeoning. Versatile (1d8).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.SP, 2)).weight(4).build(),
            EquipmentEntity.builder().name("Sickle").description("Simple melee weapon. 1d4 slashing. Light.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 1)).weight(2).build(),
            EquipmentEntity.builder().name("Spear").description("Simple melee weapon. 1d6 piercing. Thrown (20/60), versatile (1d8).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 1)).weight(3).build(),
            // Simple Ranged Weapons
            EquipmentEntity.builder().name("Light Crossbow").description("Simple ranged weapon. 1d8 piercing. Ammunition (80/320), loading, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 25)).weight(5).build(),
            EquipmentEntity.builder().name("Dart").description("Simple ranged weapon. 1d4 piercing. Finesse, thrown (20/60).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.CP, 5)).weight(0).build(),
            EquipmentEntity.builder().name("Shortbow").description("Simple ranged weapon. 1d6 piercing. Ammunition (80/320), two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 25)).weight(2).build(),
            EquipmentEntity.builder().name("Sling").description("Simple ranged weapon. 1d4 bludgeoning. Ammunition (30/120).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.SP, 1)).weight(0).build(),
            // Martial Melee Weapons
            EquipmentEntity.builder().name("Battleaxe").description("Martial melee weapon. 1d8 slashing. Versatile (1d10).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(4).build(),
            EquipmentEntity.builder().name("Flail").description("Martial melee weapon. 1d8 bludgeoning.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(2).build(),
            EquipmentEntity.builder().name("Glaive").description("Martial melee weapon. 1d10 slashing. Heavy, reach, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 20)).weight(6).build(),
            EquipmentEntity.builder().name("Greataxe").description("Martial melee weapon. 1d12 slashing. Heavy, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 30)).weight(7).build(),
            EquipmentEntity.builder().name("Greatsword").description("Martial melee weapon. 2d6 slashing. Heavy, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 50)).weight(6).build(),
            EquipmentEntity.builder().name("Halberd").description("Martial melee weapon. 1d10 slashing. Heavy, reach, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 20)).weight(6).build(),
            EquipmentEntity.builder().name("Lance").description("Martial melee weapon. 1d12 piercing. Reach, special.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(6).build(),
            EquipmentEntity.builder().name("Longsword").description("Martial melee weapon. 1d8 slashing. Versatile (1d10).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 15)).weight(3).build(),
            EquipmentEntity.builder().name("Maul").description("Martial melee weapon. 2d6 bludgeoning. Heavy, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(10).build(),
            EquipmentEntity.builder().name("Morningstar").description("Martial melee weapon. 1d8 piercing.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 15)).weight(4).build(),
            EquipmentEntity.builder().name("Pike").description("Martial melee weapon. 1d10 piercing. Heavy, reach, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 5)).weight(18).build(),
            EquipmentEntity.builder().name("Rapier").description("Martial melee weapon. 1d8 piercing. Finesse.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 25)).weight(2).build(),
            EquipmentEntity.builder().name("Scimitar").description("Martial melee weapon. 1d6 slashing. Finesse, light.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 25)).weight(3).build(),
            EquipmentEntity.builder().name("Shortsword").description("Martial melee weapon. 1d6 piercing. Finesse, light.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(2).build(),
            EquipmentEntity.builder().name("Trident").description("Martial melee weapon. 1d6 piercing. Thrown (20/60), versatile (1d8).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 5)).weight(4).build(),
            EquipmentEntity.builder().name("War Pick").description("Martial melee weapon. 1d8 piercing.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 5)).weight(2).build(),
            EquipmentEntity.builder().name("Warhammer").description("Martial melee weapon. 1d8 bludgeoning. Versatile (1d10).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 15)).weight(2).build(),
            EquipmentEntity.builder().name("Whip").description("Martial melee weapon. 1d4 slashing. Finesse, reach.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 2)).weight(3).build(),
            // Martial Ranged Weapons
            EquipmentEntity.builder().name("Blowgun").description("Martial ranged weapon. 1 piercing. Ammunition (25/100), loading.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 10)).weight(1).build(),
            EquipmentEntity.builder().name("Hand Crossbow").description("Martial ranged weapon. 1d6 piercing. Ammunition (30/120), light, loading.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 75)).weight(3).build(),
            EquipmentEntity.builder().name("Heavy Crossbow").description("Martial ranged weapon. 1d10 piercing. Ammunition (100/400), heavy, loading, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 50)).weight(18).build(),
            EquipmentEntity.builder().name("Longbow").description("Martial ranged weapon. 1d8 piercing. Ammunition (150/600), heavy, two-handed.").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 50)).weight(2).build(),
            EquipmentEntity.builder().name("Net").description("Martial ranged weapon. Special. Thrown (5/15).").equipmentCategory(EquipmentCategoryEnum.WEAPON).price(Map.of(CoinEnum.GP, 1)).weight(3).build()
        );
    }

    private List<EquipmentEntity> createPacks() {
        return List.of(
            EquipmentEntity.builder().name("Burglar's Pack").description("Includes a backpack, bag of 1,000 ball bearings, 10 feet of string, bell, 5 candles, crowbar, hammer, 10 pitons, hooded lantern, 2 flasks of oil, 5 days rations, tinderbox, waterskin, 50 feet of hempen rope.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 16)).build(),
            EquipmentEntity.builder().name("Diplomat's Pack").description("Includes a chest, 2 cases for maps and scrolls, a set of fine clothes, a bottle of ink, an ink pen, a lamp, 2 flasks of oil, 5 sheets of paper, a vial of perfume, sealing wax, and soap.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 39)).build(),
            EquipmentEntity.builder().name("Dungeoneer's Pack").description("Includes a backpack, crowbar, hammer, 10 pitons, 10 torches, tinderbox, 10 days of rations, waterskin, 50 feet of hempen rope.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 12)).build(),
            EquipmentEntity.builder().name("Entertainer's Pack").description("Includes a backpack, bedroll, 2 costumes, 5 candles, 5 days of rations, waterskin, and a disguise kit.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 40)).build(),
            EquipmentEntity.builder().name("Explorer's Pack").description("Includes a backpack, bedroll, mess kit, tinderbox, 10 torches, 10 days of rations, waterskin, 50 feet of hempen rope.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 10)).build(),
            EquipmentEntity.builder().name("Priest's Pack").description("Includes a backpack, blanket, 10 candles, tinderbox, alms box, 2 blocks of incense, censer, vestments, 2 days of rations, waterskin.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 19)).build(),
            EquipmentEntity.builder().name("Scholar's Pack").description("Includes a backpack, book of lore, bottle of ink, ink pen, 10 sheets of parchment, little bag of sand, small knife.").equipmentCategory(EquipmentCategoryEnum.PACK).price(Map.of(CoinEnum.GP, 40)).build()
        );
    }

    private List<EquipmentEntity> createTools() {
        return List.of(
            EquipmentEntity.builder().name("Thieves' Tools").description("This set of tools includes a small file, a set of lock picks, a small mirror, a set of narrow-bladed scissors, and a pair of pliers.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 25)).weight(1).build(),
            EquipmentEntity.builder().name("Herbalism Kit").description("This kit contains a variety of instruments such as clippers, mortar and pestle, pouches, and vials used by herbalists.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 5)).weight(3).build(),
            EquipmentEntity.builder().name("Alchemist's Supplies").description("These supplies enable a character to produce useful concoctions.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 50)).weight(8).build(),
            EquipmentEntity.builder().name("Smith's Tools").description("Smith's tools allow you to work metal.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 20)).weight(8).build(),
            EquipmentEntity.builder().name("Brewer's Supplies").description("Brewing is the art of producing beer.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 20)).weight(9).build(),
            EquipmentEntity.builder().name("Calligrapher's Supplies").description("Calligraphy treats writing as an elegant art.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 10)).weight(5).build(),
            EquipmentEntity.builder().name("Carpenter's Tools").description("These tools are used to build wooden structures.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 8)).weight(6).build(),
            EquipmentEntity.builder().name("Cartographer's Tools").description("These tools are used to draw maps.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 15)).weight(6).build(),
            EquipmentEntity.builder().name("Cobbler's Tools").description("These tools are used to make and repair shoes.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 5)).weight(5).build(),
            EquipmentEntity.builder().name("Cook's Utensils").description("Adventuring is hard work. With a cook along on the journey, meals will be more nutritious.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 1)).weight(8).build(),
            EquipmentEntity.builder().name("Glassblower's Tools").description("These tools are used to craft glass objects.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 30)).weight(5).build(),
            EquipmentEntity.builder().name("Jeweler's Tools").description("These tools are used to cut gems and craft jewelry.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 25)).weight(2).build(),
            EquipmentEntity.builder().name("Leatherworker's Tools").description("These tools are used to work leather.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 5)).weight(5).build(),
            EquipmentEntity.builder().name("Mason's Tools").description("These tools are used to work with stone.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 10)).weight(8).build(),
            EquipmentEntity.builder().name("Painter's Supplies").description("These supplies enable a character to create art.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 10)).weight(5).build(),
            EquipmentEntity.builder().name("Potter's Tools").description("These tools are used to craft pottery and ceramics.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 10)).weight(3).build(),
            EquipmentEntity.builder().name("Tinker's Tools").description("These tools are used to repair many kinds of items.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 50)).weight(10).build(),
            EquipmentEntity.builder().name("Weaver's Tools").description("These tools are used to create cloth and textiles.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 1)).weight(5).build(),
            EquipmentEntity.builder().name("Woodcarver's Tools").description("These tools are used to carve wood.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 1)).weight(5).build(),
            EquipmentEntity.builder().name("Disguise Kit").description("This pouch of cosmetics, hair dye, and small props lets you create disguises.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 25)).weight(3).build(),
            EquipmentEntity.builder().name("Forgery Kit").description("This small box contains papers, pens, seals, and other supplies for creating forgeries.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 15)).weight(5).build(),
            EquipmentEntity.builder().name("Gaming Set (Dice)").description("A set of dice for playing games of chance.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.SP, 1)).weight(0).build(),
            EquipmentEntity.builder().name("Gaming Set (Playing Cards)").description("A deck of cards for playing games.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.SP, 5)).weight(0).build(),
            EquipmentEntity.builder().name("Navigator's Tools").description("These tools are used for navigation at sea.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 25)).weight(2).build(),
            EquipmentEntity.builder().name("Poisoner's Kit").description("A poisoner's kit includes the vials, chemicals, and other equipment necessary for creating poisons.").equipmentCategory(EquipmentCategoryEnum.TOOL).price(Map.of(CoinEnum.GP, 50)).weight(2).build()
        );
    }

    private List<EquipmentEntity> createFoci() {
        return List.of(
            // Arcane Foci
            EquipmentEntity.builder().name("Arcane Focus (Crystal)").description("An arcane focus is a special item designed to channel the power of arcane spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 10)).weight(1).build(),
            EquipmentEntity.builder().name("Arcane Focus (Orb)").description("An arcane focus is a special item designed to channel the power of arcane spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 20)).weight(3).build(),
            EquipmentEntity.builder().name("Arcane Focus (Rod)").description("An arcane focus is a special item designed to channel the power of arcane spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 10)).weight(2).build(),
            EquipmentEntity.builder().name("Arcane Focus (Staff)").description("An arcane focus is a special item designed to channel the power of arcane spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 5)).weight(4).build(),
            EquipmentEntity.builder().name("Arcane Focus (Wand)").description("An arcane focus is a special item designed to channel the power of arcane spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 10)).weight(1).build(),
            // Divine Foci
            EquipmentEntity.builder().name("Holy Symbol (Amulet)").description("A holy symbol is a representation of a god or pantheon.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 5)).weight(1).build(),
            EquipmentEntity.builder().name("Holy Symbol (Emblem)").description("A holy symbol is a representation of a god or pantheon.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 5)).weight(0).build(),
            EquipmentEntity.builder().name("Holy Symbol (Reliquary)").description("A holy symbol is a representation of a god or pantheon.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 5)).weight(2).build(),
            // Druidic Focus
            EquipmentEntity.builder().name("Druidic Focus (Sprig of Mistletoe)").description("A druidic focus might be a sprig of mistletoe or holly.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 1)).weight(0).build(),
            EquipmentEntity.builder().name("Druidic Focus (Totem)").description("A druidic focus might be a totem object incorporating feathers, fur, bones, or teeth.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 1)).weight(0).build(),
            EquipmentEntity.builder().name("Druidic Focus (Wooden Staff)").description("A druidic focus might be a wooden staff drawn whole from a living tree.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 5)).weight(4).build(),
            EquipmentEntity.builder().name("Druidic Focus (Yew Wand)").description("A druidic focus might be a yew wand.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 10)).weight(1).build(),
            // Component Pouch
            EquipmentEntity.builder().name("Component Pouch").description("A component pouch is a small, watertight leather belt pouch that has compartments to hold all the material components and other special items you need to cast your spells.").equipmentCategory(EquipmentCategoryEnum.FOCUS).price(Map.of(CoinEnum.GP, 25)).weight(2).build(),
            // Spellbook
            EquipmentEntity.builder().name("Spellbook").description("Essential for wizards, a spellbook is a leather-bound tome with 100 blank vellum pages suitable for recording spells.").equipmentCategory(EquipmentCategoryEnum.GEAR).price(Map.of(CoinEnum.GP, 50)).weight(3).build()
        );
    }

    private List<EquipmentEntity> createMusicalInstruments() {
        return List.of(
            EquipmentEntity.builder().name("Bagpipes").description("A wind instrument using enclosed reeds fed from a constant reservoir of air.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 30)).weight(6).build(),
            EquipmentEntity.builder().name("Drum").description("A percussion instrument consisting of a membrane stretched over a hollow body.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 6)).weight(3).build(),
            EquipmentEntity.builder().name("Dulcimer").description("A stringed instrument with strings stretched over a trapezoidal sounding board.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 25)).weight(10).build(),
            EquipmentEntity.builder().name("Flute").description("A wind instrument that produces sound from the flow of air across an opening.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 2)).weight(1).build(),
            EquipmentEntity.builder().name("Lute").description("A stringed instrument with a neck and a deep round back.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 35)).weight(2).build(),
            EquipmentEntity.builder().name("Lyre").description("A stringed instrument with a yoke consisting of two arms and a crossbar.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 30)).weight(2).build(),
            EquipmentEntity.builder().name("Horn").description("A wind instrument made of brass or horn.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 3)).weight(2).build(),
            EquipmentEntity.builder().name("Pan Flute").description("A wind instrument made from several pipes of gradually increasing length.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 12)).weight(2).build(),
            EquipmentEntity.builder().name("Shawm").description("A loud double-reed woodwind instrument.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 2)).weight(1).build(),
            EquipmentEntity.builder().name("Viol").description("A stringed instrument played with a bow.").equipmentCategory(EquipmentCategoryEnum.MUSICAL_INSTRUMENT).price(Map.of(CoinEnum.GP, 30)).weight(1).build()
        );
    }

    private List<EquipmentEntity> createAmmunition() {
        return List.of(
            EquipmentEntity.builder().name("Arrows (20)").description("20 arrows for bows.").equipmentCategory(EquipmentCategoryEnum.AMMUNITION).price(Map.of(CoinEnum.GP, 1)).weight(1).build(),
            EquipmentEntity.builder().name("Blowgun Needles (50)").description("50 needles for blowguns.").equipmentCategory(EquipmentCategoryEnum.AMMUNITION).price(Map.of(CoinEnum.GP, 1)).weight(1).build(),
            EquipmentEntity.builder().name("Crossbow Bolts (20)").description("20 bolts for crossbows.").equipmentCategory(EquipmentCategoryEnum.AMMUNITION).price(Map.of(CoinEnum.GP, 1)).weight(2).build(),
            EquipmentEntity.builder().name("Sling Bullets (20)").description("20 bullets for slings.").equipmentCategory(EquipmentCategoryEnum.AMMUNITION).price(Map.of(CoinEnum.CP, 4)).weight(2).build(),
            EquipmentEntity.builder().name("Quiver").description("A quiver can hold up to 20 arrows.").equipmentCategory(EquipmentCategoryEnum.GEAR).price(Map.of(CoinEnum.GP, 1)).weight(1).build()
        );
    }

    private List<EquipmentEntity> createMagicArmor() {
        return List.of(
            // Armor +1/+2/+3
            EquipmentEntity.builder().name("Armor +1").description("You have a +1 bonus to AC while wearing this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Armor +2").description("You have a +2 bonus to AC while wearing this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Armor +3").description("You have a +3 bonus to AC while wearing this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            // Shields
            EquipmentEntity.builder().name("Shield +1").description("While holding this shield, you have a +1 bonus to AC in addition to the shield's normal bonus.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Shield +2").description("While holding this shield, you have a +2 bonus to AC in addition to the shield's normal bonus.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Shield +3").description("While holding this shield, you have a +3 bonus to AC in addition to the shield's normal bonus.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            // Special Armor
            EquipmentEntity.builder().name("Adamantine Armor").description("This suit of armor is reinforced with adamantine. While wearing it, any critical hit against you becomes a normal hit.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Animated Shield").description("While holding this shield, you can speak its command word as a bonus action to cause it to animate.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Armor of Invulnerability").description("You have resistance to nonmagical damage while you wear this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Armor of Resistance").description("You have resistance to one type of damage while you wear this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Armor of Vulnerability").description("While wearing this armor, you have resistance to one damage type but vulnerability to two others.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Arrow-Catching Shield").description("You gain a +2 bonus to AC against ranged attacks while you wield this shield.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Demon Armor").description("While wearing this armor, you gain a +1 bonus to AC, and you can understand and speak Abyssal.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Dragon Scale Mail").description("Dragon scale mail is made of the scales of one kind of dragon.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Dwarven Plate").description("While wearing this armor, you gain a +2 bonus to AC.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Efreeti Chain").description("While wearing this armor, you gain a +3 bonus to AC, you are immune to fire damage.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Elven Chain").description("You gain a +1 bonus to AC while you wear this armor.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Glamoured Studded Leather").description("While wearing this armor, you gain a +1 bonus to AC. You can also use a bonus action to change its appearance.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Mithral Armor").description("Mithral is a light, flexible metal. If the armor normally imposes disadvantage on Stealth checks, the mithral version does not.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Plate Armor of Etherealness").description("While wearing this armor, you can speak its command word as an action to cast etherealness.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Sentinel Shield").description("While holding this shield, you have advantage on initiative rolls and Wisdom (Perception) checks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Shield of Missile Attraction").description("While holding this shield, you have resistance to damage from ranged weapon attacks.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build(),
            EquipmentEntity.builder().name("Spellguard Shield").description("While holding this shield, you have advantage on saving throws against spells and other magical effects.").equipmentCategory(EquipmentCategoryEnum.ARMOR).build()
        );
    }

    private List<EquipmentEntity> createMagicWeapons() {
        return List.of(
            // Magic Weapons
            EquipmentEntity.builder().name("Weapon +1").description("You have a +1 bonus to attack and damage rolls made with this magic weapon.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Weapon +2").description("You have a +2 bonus to attack and damage rolls made with this magic weapon.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Weapon +3").description("You have a +3 bonus to attack and damage rolls made with this magic weapon.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            // Special Weapons
            EquipmentEntity.builder().name("Berserker Axe").description("You gain a +1 bonus to attack and damage rolls. When you take damage from a creature, you must make a DC 15 Wisdom save or go berserk.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.SLASHING, 1)).build(),
            EquipmentEntity.builder().name("Dancing Sword").description("You can use a bonus action to toss this magic sword into the air and speak the command word.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Defender").description("You gain a +3 bonus to attack and damage rolls. The first time you attack on your turn, you can transfer some or all of the bonus to AC.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Dragon Slayer").description("You gain a +1 bonus to attack and damage rolls. When you hit a dragon, it takes an extra 3d6 damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Dwarven Thrower").description("You gain a +3 bonus to attack and damage rolls. It has the thrown property with a range of 20/60 feet.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Flame Tongue").description("You can use a bonus action to speak this magic sword's command word, causing flames to erupt from the blade.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.FIRE, 2)).build(),
            EquipmentEntity.builder().name("Frost Brand").description("When you hit with an attack using this magic sword, the target takes an extra 1d6 cold damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.COLD, 1)).build(),
            EquipmentEntity.builder().name("Giant Slayer").description("You gain a +1 bonus to attack and damage rolls. When you hit a giant, it takes an extra 2d6 damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Holy Avenger").description("You gain a +3 bonus to attack and damage rolls. When you hit a fiend or undead, it takes an extra 2d10 radiant damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.RADIANT, 2)).build(),
            EquipmentEntity.builder().name("Javelin of Lightning").description("When you hurl it and speak its command word, it transforms into a bolt of lightning.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.LIGHTNING, 4)).build(),
            EquipmentEntity.builder().name("Luck Blade").description("You gain a +1 bonus to attack and damage rolls. While the sword is on your person, you also gain a +1 bonus to saving throws.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Mace of Disruption").description("When you hit a fiend or undead with this magic weapon, that creature takes an extra 2d6 radiant damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.RADIANT, 2)).build(),
            EquipmentEntity.builder().name("Mace of Smiting").description("You gain a +1 bonus to attack and damage rolls. The bonus increases to +3 when you use it to attack a construct.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Mace of Terror").description("This magic weapon has 3 charges. While holding it, you can use an action to expend 1 charge to release a wave of terror.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Nine Lives Stealer").description("You gain a +2 bonus to attack and damage rolls. The sword has 1d8+1 charges.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Oathbow").description("When you nock an arrow on this bow, it whispers in Elvish 'Swift death to you who have wronged me.'").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Scimitar of Speed").description("You gain a +2 bonus to attack and damage rolls. In addition, you can make one attack as a bonus action on each of your turns.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Sun Blade").description("This item appears to be a longsword hilt. While grasping the hilt, you can use a bonus action to cause a blade of pure radiance to spring into existence.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.RADIANT, 2)).build(),
            EquipmentEntity.builder().name("Sword of Life Stealing").description("When you attack a creature with this magic weapon and roll a 20, the target takes an extra 3d6 necrotic damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).damageTypeAndBonus(Map.of(DamageTypeEnum.NECROTIC, 3)).build(),
            EquipmentEntity.builder().name("Sword of Sharpness").description("When you attack an object with this magic sword and hit, maximize your weapon damage dice against the target.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Sword of Wounding").description("Hit points lost to this weapon's damage can be regained only through a short or long rest.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Trident of Fish Command").description("This trident is a magic weapon. It has 3 charges.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Vicious Weapon").description("When you roll a 20 on your attack roll with this magic weapon, your critical hit deals an extra 2d6 damage.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Vorpal Sword").description("You gain a +3 bonus to attack and damage rolls. When you roll a 20 on an attack roll, the sword may sever the target's head.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            // Ammunition
            EquipmentEntity.builder().name("Ammunition +1").description("You have a +1 bonus to attack and damage rolls made with this piece of magic ammunition.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Ammunition +2").description("You have a +2 bonus to attack and damage rolls made with this piece of magic ammunition.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Ammunition +3").description("You have a +3 bonus to attack and damage rolls made with this piece of magic ammunition.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build(),
            EquipmentEntity.builder().name("Arrow of Slaying").description("An arrow of slaying is a magic weapon meant to slay a particular kind of creature.").equipmentCategory(EquipmentCategoryEnum.WEAPON).build()
        );
    }

    private List<EquipmentEntity> createPotions() {
        return List.of(
            EquipmentEntity.builder().name("Potion of Healing").description("You regain 2d4 + 2 hit points when you drink this potion.").equipmentCategory(EquipmentCategoryEnum.POTION).price(Map.of(CoinEnum.GP, 50)).build(),
            EquipmentEntity.builder().name("Potion of Greater Healing").description("You regain 4d4 + 4 hit points when you drink this potion.").equipmentCategory(EquipmentCategoryEnum.POTION).price(Map.of(CoinEnum.GP, 100)).build(),
            EquipmentEntity.builder().name("Potion of Superior Healing").description("You regain 8d4 + 8 hit points when you drink this potion.").equipmentCategory(EquipmentCategoryEnum.POTION).price(Map.of(CoinEnum.GP, 500)).build(),
            EquipmentEntity.builder().name("Potion of Supreme Healing").description("You regain 10d4 + 20 hit points when you drink this potion.").equipmentCategory(EquipmentCategoryEnum.POTION).price(Map.of(CoinEnum.GP, 1350)).build(),
            EquipmentEntity.builder().name("Potion of Animal Friendship").description("When you drink this potion, you can cast the animal friendship spell for 1 hour at will.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Clairvoyance").description("When you drink this potion, you gain the effect of the clairvoyance spell.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Climbing").description("When you drink this potion, you gain a climbing speed equal to your walking speed for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Diminution").description("When you drink this potion, you gain the 'reduce' effect of the enlarge/reduce spell for 1d4 hours.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Fire Breath").description("After drinking this potion, you can use a bonus action to exhale fire at a target within 30 feet.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Fire Resistance").description("When you drink this potion, you gain resistance to fire damage for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Flying").description("When you drink this potion, you gain a flying speed equal to your walking speed for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Gaseous Form").description("When you drink this potion, you gain the effect of the gaseous form spell for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Hill)").description("When you drink this potion, your Strength score changes to 21 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Frost)").description("When you drink this potion, your Strength score changes to 23 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Stone)").description("When you drink this potion, your Strength score changes to 23 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Fire)").description("When you drink this potion, your Strength score changes to 25 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Cloud)").description("When you drink this potion, your Strength score changes to 27 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Giant Strength (Storm)").description("When you drink this potion, your Strength score changes to 29 for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Growth").description("When you drink this potion, you gain the 'enlarge' effect of the enlarge/reduce spell for 1d4 hours.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Heroism").description("For 1 hour after drinking it, you gain 10 temporary hit points and are under the effect of the bless spell.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Invisibility").description("This potion's container looks empty. When you drink it, you become invisible for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Invulnerability").description("For 1 minute after you drink this potion, you have resistance to all damage.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Longevity").description("When you drink this potion, your physical age is reduced by 1d6 + 6 years.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Mind Reading").description("When you drink this potion, you gain the effect of the detect thoughts spell.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Poison").description("This concoction looks, smells, and tastes like a potion of healing. It is actually poison.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Resistance").description("When you drink this potion, you gain resistance to one type of damage for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Speed").description("When you drink this potion, you gain the effect of the haste spell for 1 minute.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Vitality").description("When you drink this potion, it removes any exhaustion you are suffering and cures any disease or poison affecting you.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Potion of Water Breathing").description("You can breathe underwater for 1 hour after drinking this potion.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Elixir of Health").description("When you drink this potion, it cures any disease afflicting you, and it removes the blinded, deafened, paralyzed, and poisoned conditions.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Oil of Etherealness").description("This cloudy gray oil can cover a Medium or smaller creature. The creature becomes ethereal for 1 hour.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Oil of Sharpness").description("This clear, gelatinous oil sparkles with tiny, ultrathin silver shards. The coated weapon is treated as a +3 magic weapon.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Oil of Slipperiness").description("This sticky black unguent is thick and heavy in the container, but flows quickly when poured.").equipmentCategory(EquipmentCategoryEnum.POTION).build(),
            EquipmentEntity.builder().name("Philter of Love").description("The next time you see a creature within 10 minutes after drinking this philter, you become charmed by that creature.").equipmentCategory(EquipmentCategoryEnum.POTION).build()
        );
    }

    private List<EquipmentEntity> createRings() {
        return List.of(
            EquipmentEntity.builder().name("Ring of Animal Influence").description("This ring has 3 charges. While wearing it, you can cast certain spells.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Djinni Summoning").description("While wearing this ring, you can speak its command word as an action to summon a particular djinni.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Elemental Command (Air)").description("This ring is linked to the Elemental Plane of Air.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Elemental Command (Earth)").description("This ring is linked to the Elemental Plane of Earth.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Elemental Command (Fire)").description("This ring is linked to the Elemental Plane of Fire.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Elemental Command (Water)").description("This ring is linked to the Elemental Plane of Water.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Evasion").description("This ring has 3 charges. When you fail a Dexterity saving throw, you can use your reaction to expend 1 charge to succeed instead.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Feather Falling").description("When you fall while wearing this ring, you descend 60 feet per round and take no falling damage.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Free Action").description("While wearing this ring, difficult terrain doesn't cost you extra movement.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Invisibility").description("While wearing this ring, you can turn invisible as an action.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Jumping").description("While wearing this ring, you can cast the jump spell from it as a bonus action at will.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Mind Shielding").description("While wearing this ring, you are immune to magic that allows other creatures to read your thoughts.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Protection").description("You gain a +1 bonus to AC and saving throws while wearing this ring.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Regeneration").description("While wearing this ring, you regain 1d6 hit points every 10 minutes.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Resistance").description("You have resistance to one damage type while wearing this ring.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Shooting Stars").description("While wearing this ring in dim light or darkness, you can cast dancing lights and light at will.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Spell Storing").description("This ring stores spells cast into it, holding them until the attuned wearer uses them.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Spell Turning").description("While wearing this ring, you have advantage on saving throws against any spell that targets only you.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Swimming").description("You have a swimming speed of 40 feet while wearing this ring.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Telekinesis").description("While wearing this ring, you can cast the telekinesis spell at will.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of the Ram").description("This ring has 3 charges. While wearing it, you can use an action to expend charges to make a ranged spell attack.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Three Wishes").description("While wearing this ring, you can use an action to expend 1 of its 3 charges to cast the wish spell.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Warmth").description("While wearing this ring, you have resistance to cold damage.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of Water Walking").description("While wearing this ring, you can stand on and move across any liquid surface as if it were solid ground.").equipmentCategory(EquipmentCategoryEnum.RING).build(),
            EquipmentEntity.builder().name("Ring of X-ray Vision").description("While wearing this ring, you can use an action to speak its command word to see into and through solid matter.").equipmentCategory(EquipmentCategoryEnum.RING).build()
        );
    }

    private List<EquipmentEntity> createRods() {
        return List.of(
            EquipmentEntity.builder().name("Immovable Rod").description("This flat iron rod has a button on one end. When you press the button, it becomes magically fixed in place.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Absorption").description("While holding this rod, you can use your reaction to absorb a spell that targets only you.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Alertness").description("This rod has a flanged head, and it functions as a magic mace that grants a +1 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Lordly Might").description("This rod has a flanged head, and it functions as a magic mace that grants a +3 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Pact Keeper +1").description("While holding this rod, you gain a +1 bonus to spell attack rolls and to the saving throw DCs of your warlock spells.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Pact Keeper +2").description("While holding this rod, you gain a +2 bonus to spell attack rolls and to the saving throw DCs of your warlock spells.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Pact Keeper +3").description("While holding this rod, you gain a +3 bonus to spell attack rolls and to the saving throw DCs of your warlock spells.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Resurrection").description("The rod has 5 charges. While you hold it, you can use an action to cast one of the following spells from it.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Rulership").description("You can use an action to present the rod and command obedience from each creature of your choice.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of Security").description("While holding this rod, you can use an action to activate it.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Rod of the Pact Keeper").description("While holding this rod, you gain a bonus to spell attack rolls and saving throw DCs of your warlock spells.").equipmentCategory(EquipmentCategoryEnum.ROD).build(),
            EquipmentEntity.builder().name("Tentacle Rod").description("Made by the drow, this rod is a magic weapon that ends in three rubbery tentacles.").equipmentCategory(EquipmentCategoryEnum.ROD).build()
        );
    }

    private List<EquipmentEntity> createScrolls() {
        return List.of(
            EquipmentEntity.builder().name("Spell Scroll (Cantrip)").description("A spell scroll bears the words of a single cantrip.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 25)).build(),
            EquipmentEntity.builder().name("Spell Scroll (1st Level)").description("A spell scroll bears the words of a single 1st-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 75)).build(),
            EquipmentEntity.builder().name("Spell Scroll (2nd Level)").description("A spell scroll bears the words of a single 2nd-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 150)).build(),
            EquipmentEntity.builder().name("Spell Scroll (3rd Level)").description("A spell scroll bears the words of a single 3rd-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 300)).build(),
            EquipmentEntity.builder().name("Spell Scroll (4th Level)").description("A spell scroll bears the words of a single 4th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 500)).build(),
            EquipmentEntity.builder().name("Spell Scroll (5th Level)").description("A spell scroll bears the words of a single 5th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 1000)).build(),
            EquipmentEntity.builder().name("Spell Scroll (6th Level)").description("A spell scroll bears the words of a single 6th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 2000)).build(),
            EquipmentEntity.builder().name("Spell Scroll (7th Level)").description("A spell scroll bears the words of a single 7th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 5000)).build(),
            EquipmentEntity.builder().name("Spell Scroll (8th Level)").description("A spell scroll bears the words of a single 8th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 10000)).build(),
            EquipmentEntity.builder().name("Spell Scroll (9th Level)").description("A spell scroll bears the words of a single 9th-level spell.").equipmentCategory(EquipmentCategoryEnum.SCROLL).price(Map.of(CoinEnum.GP, 50000)).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Aberrations)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against aberrations.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Beasts)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against beasts.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Celestials)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against celestials.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Elementals)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against elementals.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Fey)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against fey.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Fiends)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against fiends.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build(),
            EquipmentEntity.builder().name("Scroll of Protection (Undead)").description("Using an action to read the scroll, a barrier extends from you in a 5-foot radius, protecting against undead.").equipmentCategory(EquipmentCategoryEnum.SCROLL).build()
        );
    }

    private List<EquipmentEntity> createStaffs() {
        return List.of(
            EquipmentEntity.builder().name("Staff of Charming").description("While holding this staff, you can use an action to expend 1 of its 10 charges to cast charm person.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Fire").description("You have resistance to fire damage while you hold this staff.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Frost").description("You have resistance to cold damage while you hold this staff.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Healing").description("This staff has 10 charges. While holding it, you can use an action to expend charges to cast cure wounds.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Power").description("This staff can be wielded as a magic quarterstaff that grants a +2 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Striking").description("This staff can be wielded as a magic quarterstaff that grants a +3 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Swarming Insects").description("This staff has 10 charges and regains 1d6 + 4 expended charges daily at dawn.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of the Magi").description("This staff can be wielded as a magic quarterstaff that grants a +2 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of the Python").description("You can use an action to speak this staff's command word and throw the staff on the ground within 10 feet.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of the Woodlands").description("This staff can be wielded as a magic quarterstaff that grants a +2 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Thunder and Lightning").description("This staff can be wielded as a magic quarterstaff that grants a +2 bonus to attack and damage rolls.").equipmentCategory(EquipmentCategoryEnum.STAFF).build(),
            EquipmentEntity.builder().name("Staff of Withering").description("This staff has 3 charges and regains 1d3 expended charges daily at dawn.").equipmentCategory(EquipmentCategoryEnum.STAFF).build()
        );
    }

    private List<EquipmentEntity> createWands() {
        return List.of(
            EquipmentEntity.builder().name("Wand of Binding").description("This wand has 7 charges. While holding it, you can use an action to expend charges to cast hold monster or hold person.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Enemy Detection").description("This wand has 7 charges. While holding it, you can use an action and expend 1 charge to speak its command word.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Fear").description("This wand has 7 charges. While holding it, you can use an action to expend 1 charge to cast command.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Fireballs").description("This wand has 7 charges. While holding it, you can use an action to expend charges to cast the fireball spell.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Lightning Bolts").description("This wand has 7 charges. While holding it, you can use an action to expend charges to cast the lightning bolt spell.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Magic Detection").description("This wand has 3 charges. While holding it, you can expend 1 charge as an action to cast the detect magic spell.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Magic Missiles").description("This wand has 7 charges. While holding it, you can use an action to expend charges to cast magic missile.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Paralysis").description("This wand has 7 charges. While holding it, you can use an action to expend 1 charge to cause a ray to streak from the tip.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Polymorph").description("This wand has 7 charges. While holding it, you can use an action to expend 1 charge to cast polymorph.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Secrets").description("The wand has 3 charges. While holding it, you can use an action to expend 1 charge.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of the War Mage +1").description("While holding this wand, you gain a +1 bonus to spell attack rolls.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of the War Mage +2").description("While holding this wand, you gain a +2 bonus to spell attack rolls.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of the War Mage +3").description("While holding this wand, you gain a +3 bonus to spell attack rolls.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Web").description("This wand has 7 charges. While holding it, you can use an action to expend 1 charge to cast web.").equipmentCategory(EquipmentCategoryEnum.WAND).build(),
            EquipmentEntity.builder().name("Wand of Wonder").description("This wand has 7 charges. While holding it, you can use an action to expend 1 charge and choose a target.").equipmentCategory(EquipmentCategoryEnum.WAND).build()
        );
    }

    private List<EquipmentEntity> createWondrousItems() {
        return List.of(
            // Amulets and Necklaces
            EquipmentEntity.builder().name("Amulet of Health").description("Your Constitution score is 19 while you wear this amulet.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Amulet of Proof Against Detection and Location").description("While wearing this amulet, you are hidden from divination magic.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Amulet of the Planes").description("While wearing this amulet, you can use an action to name a location that you are familiar with on another plane.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Necklace of Adaptation").description("While wearing this necklace, you can breathe normally in any environment.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Necklace of Fireballs").description("This necklace has 1d6+3 beads hanging from it. You can use an action to detach a bead and throw it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Necklace of Prayer Beads").description("This necklace has 1d4+2 magic beads made from aquamarine, black pearl, or topaz.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Periapt of Health").description("You are immune to contracting any disease while you wear this pendant.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Periapt of Proof Against Poison").description("This delicate silver chain has a brilliant-cut black gem pendant. You are immune to poison.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Periapt of Wound Closure").description("While you wear this pendant, you stabilize whenever you are dying at the start of your turn.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Medallion of Thoughts").description("The medallion has 3 charges. While wearing it, you can use an action to expend 1 charge to cast detect thoughts.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Scarab of Protection").description("If you hold this medallion-sized beetle-shaped scarab in your hand for 1 round, an inscription appears.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Belts and Girdles
            EquipmentEntity.builder().name("Belt of Dwarvenkind").description("While wearing this belt, you gain advantage on Charisma (Persuasion) checks made to interact with dwarves.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Hill)").description("While wearing this belt, your Strength score changes to 21.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Stone)").description("While wearing this belt, your Strength score changes to 23.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Frost)").description("While wearing this belt, your Strength score changes to 23.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Fire)").description("While wearing this belt, your Strength score changes to 25.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Cloud)").description("While wearing this belt, your Strength score changes to 27.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Belt of Giant Strength (Storm)").description("While wearing this belt, your Strength score changes to 29.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Boots
            EquipmentEntity.builder().name("Boots of Elvenkind").description("While you wear these boots, your steps make no sound.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Boots of Levitation").description("While you wear these boots, you can use an action to cast the levitate spell on yourself at will.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Boots of Speed").description("While you wear these boots, you can use a bonus action and click the boots' heels together.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Boots of Striding and Springing").description("While you wear these boots, your walking speed becomes 30 feet.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Boots of the Winterlands").description("These furred boots are snug and feel quite warm.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Winged Boots").description("While you wear these boots, you have a flying speed equal to your walking speed.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Cloaks
            EquipmentEntity.builder().name("Cloak of Arachnida").description("This fine garment is made of black silk interwoven with faint silvery threads.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of Displacement").description("While you wear this cloak, it projects an illusion that makes you appear to be standing nearby.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of Elvenkind").description("While you wear this cloak with its hood up, Wisdom (Perception) checks made to see you have disadvantage.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of Invisibility").description("While wearing this cloak, you can pull its hood over your head to cause yourself to become invisible.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of Many Fashions").description("While wearing this cloak, you can use a bonus action to change the style, color, and apparent quality of the garment.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of Protection").description("You gain a +1 bonus to AC and saving throws while you wear this cloak.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of the Bat").description("While wearing this cloak, you have advantage on Dexterity (Stealth) checks.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cloak of the Manta Ray").description("While wearing this cloak with its hood up, you can breathe underwater and gain a swimming speed of 60 feet.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Robe of Eyes").description("This robe is adorned with eyelike patterns. While you wear the robe, you gain several benefits.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Robe of Scintillating Colors").description("This robe has 3 charges. While you wear it, you can use an action to expend 1 charge.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Robe of Stars").description("This black or dark blue robe is embroidered with small white or silver stars.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Robe of the Archmagi").description("This elegant garment is made from exquisite cloth and adorned with runes.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Robe of Useful Items").description("This robe has cloth patches of various shapes and colors covering it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Gloves and Gauntlets
            EquipmentEntity.builder().name("Gauntlets of Ogre Power").description("Your Strength score is 19 while you wear these gauntlets.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Gloves of Missile Snaring").description("These gloves seem to almost meld into your hands when you don them.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Gloves of Swimming and Climbing").description("While wearing these gloves, climbing and swimming don't cost you extra movement.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Gloves of Thievery").description("These gloves are invisible while worn. While wearing them, you gain a +5 bonus to Sleight of Hand checks.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Headwear
            EquipmentEntity.builder().name("Circlet of Blasting").description("While wearing this circlet, you can use an action to cast the scorching ray spell.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Diadem of Wizardry").description("While wearing the diadem, you can use it as a spellcasting focus for your wizard spells.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Hat of Disguise").description("While wearing this hat, you can use an action to cast the disguise self spell from it at will.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Headband of Intellect").description("Your Intelligence score is 19 while you wear this headband.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Helm of Brilliance").description("This dazzling helm is set with 1d10 diamonds, 2d10 rubies, 3d10 fire opals, and 4d10 opals.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Helm of Comprehending Languages").description("While wearing this helm, you can use an action to cast the comprehend languages spell from it at will.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Helm of Telepathy").description("While wearing this helm, you can use an action to cast the detect thoughts spell from it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Helm of Teleportation").description("This helm has 3 charges. While wearing it, you can use an action to expend 1 charge to cast teleport.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Bags and Containers
            EquipmentEntity.builder().name("Bag of Beans").description("Inside this heavy cloth bag are 3d4 dry beans.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Bag of Devouring").description("This bag superficially resembles a bag of holding.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Bag of Holding").description("This bag has an interior space considerably larger than its outside dimensions.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Bag of Tricks").description("This ordinary bag, made from gray, rust, or tan cloth, appears empty.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Decanter of Endless Water").description("This stoppered flask sloshes when shaken, as if it contains water.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Eversmoking Bottle").description("Smoke leaks from the lead-stoppered mouth of this brass bottle.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Handy Haversack").description("This backpack has a central pouch and two side pouches.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Heward's Handy Haversack").description("This backpack has a central pouch and two side pouches.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Iron Flask").description("This iron bottle has a brass stopper. You can use an action to speak the flask's command word.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Portable Hole").description("This fine black cloth, soft as silk, is folded up to the dimensions of a handkerchief.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Quiver of Ehlonna").description("Each of the quiver's three compartments connects to an extradimensional space.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Eyes and Vision
            EquipmentEntity.builder().name("Eyes of Charming").description("These crystal lenses fit over the eyes. They have 3 charges.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Eyes of Minute Seeing").description("These crystal lenses fit over the eyes.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Eyes of the Eagle").description("These crystal lenses fit over the eyes.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Gem of Brightness").description("This prism has 50 charges.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Gem of Seeing").description("This gem has 3 charges. As an action, you can speak the gem's command word and expend 1 charge.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Goggles of Night").description("While wearing these dark lenses, you have darkvision out to a range of 60 feet.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Lantern of Revealing").description("While lit, this hooded lantern burns for 6 hours on 1 pint of oil.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Musical Instruments
            EquipmentEntity.builder().name("Instrument of the Bards (Anstruth Harp)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Canaith Mandolin)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Cli Lyre)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Doss Lute)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Fochlucan Bandore)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Mac-Fuirmidh Cittern)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Instrument of the Bards (Ollamh Harp)").description("An instrument of the bards is an exquisite example of its kind.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            // Miscellaneous
            EquipmentEntity.builder().name("Alchemy Jug").description("This ceramic jug appears to be able to hold a gallon of liquid and weighs 12 pounds.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Apparatus of Kwalish").description("This item first appears to be a Large sealed iron barrel weighing 500 pounds.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Bowl of Commanding Water Elementals").description("While this bowl is filled with water, you can use an action to summon a water elemental.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Brazier of Commanding Fire Elementals").description("While a fire burns in this brass brazier, you can use an action to summon a fire elemental.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Broom of Flying").description("This wooden broom functions like a mundane broom until you stand astride it and speak its command word.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Candle of Invocation").description("This slender taper is dedicated to a deity.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cape of the Mountebank").description("This cape smells faintly of brimstone. While wearing it, you can use it to cast dimension door.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Carpet of Flying").description("You can speak the carpet's command word as an action to make the carpet hover and fly.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Censer of Controlling Air Elementals").description("While incense is burning in this censer, you can use an action to summon an air elemental.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Chime of Opening").description("This hollow metal tube measures about 1 foot long and weighs 1 pound.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Crystal Ball").description("This crystal ball is about 6 inches in diameter.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cube of Force").description("This cube is about an inch across. Each face has a distinct marking on it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Cubic Gate").description("This cube is 3 inches across and radiates palpable magical energy.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Daern's Instant Fortress").description("You can use an action to place this 1-inch metal cube on the ground and speak its command word.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Deck of Illusions").description("This box contains a set of parchment cards.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Deck of Many Things").description("Usually found in a box or pouch, this deck contains a number of cards made of ivory or vellum.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Dimensional Shackles").description("You can use an action to place these shackles on an incapacitated creature.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Driftglobe").description("This small sphere of thick glass weighs 1 pound.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Dust of Disappearance").description("Found in a small packet, this powder resembles very fine sand.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Dust of Dryness").description("This small packet contains 1d6+4 pinches of dust.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Dust of Sneezing and Choking").description("Found in a small container, this powder resembles very fine sand.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Efreeti Bottle").description("This painted brass bottle weighs 1 pound.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Elemental Gem").description("This gem contains a mote of elemental energy.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Bronze Griffon)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Ebony Fly)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Golden Lions)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Ivory Goats)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Marble Elephant)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Obsidian Steed)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Onyx Dog)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Serpentine Owl)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Figurine of Wondrous Power (Silver Raven)").description("A figurine of wondrous power is a statuette of a beast.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Folding Boat").description("This object appears as a wooden box that measures 12 inches long, 6 inches wide, and 6 inches deep.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Horn of Blasting").description("You can use an action to speak the horn's command word and then blow the horn.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Horn of Valhalla").description("You can use an action to blow this horn.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Horseshoes of Speed").description("These iron horseshoes come in a set of four.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Horseshoes of a Zephyr").description("These iron horseshoes come in a set of four.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Absorption)").description("An Ioun stone is named after Ioun, a god of knowledge and prophecy.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Agility)").description("Your Dexterity score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Awareness)").description("You can't be surprised while this pale lavender ellipsoid orbits your head.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Fortitude)").description("Your Constitution score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Greater Absorption)").description("While this marbled lavender and green ellipsoid orbits your head, you can use your reaction to cancel a spell.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Insight)").description("Your Wisdom score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Intellect)").description("Your Intelligence score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Leadership)").description("Your Charisma score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Mastery)").description("Your proficiency bonus increases by 1 while this pale green prism orbits your head.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Protection)").description("You gain a +1 bonus to AC while this dusty rose prism orbits your head.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Regeneration)").description("You regain 15 hit points at the end of each hour this pearly white spindle orbits your head.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Reserve)").description("This vibrant purple prism stores spells cast into it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Strength)").description("Your Strength score increases by 2, to a maximum of 20.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Ioun Stone (Sustenance)").description("You don't need to eat or drink while this clear spindle orbits your head.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Iron Bands of Bilarro").description("This rusty iron sphere measures 3 inches in diameter and weighs 1 pound.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Mantle of Spell Resistance").description("You have advantage on saving throws against spells while you wear this cloak.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Manual of Bodily Health").description("This book contains health and diet tips.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Manual of Gainful Exercise").description("This book describes fitness exercises.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Manual of Golems").description("This tome contains information and incantations necessary to make a particular type of golem.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Manual of Quickness of Action").description("This book contains coordination and balance exercises.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Mirror of Life Trapping").description("When this 4-foot-tall mirror is viewed indirectly, its surface shows faint images of creatures.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Orb of Dragonkind").description("Ages past, elves and humans waged a terrible war against evil dragons.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Pearl of Power").description("While this pearl is on your person, you can use an action to speak its command word.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Pipes of Haunting").description("You must be proficient with wind instruments to use these pipes.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Pipes of the Sewers").description("You must be proficient with wind instruments to use these pipes.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Rope of Climbing").description("This 60-foot length of silk rope weighs 3 pounds.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Rope of Entanglement").description("This rope is 30 feet long and weighs 3 pounds.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Saddle of the Cavalier").description("While in this saddle on a mount, you can't be dismounted against your will.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Sending Stones").description("Sending stones come in pairs, with each stone carved to match the other.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Slippers of Spider Climbing").description("While you wear these light shoes, you can move up, down, and across vertical surfaces.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Sovereign Glue").description("This viscous, milky-white substance can form a permanent adhesive bond.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Sphere of Annihilation").description("This 2-foot-diameter black sphere is a hole in the multiverse.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Stone of Controlling Earth Elementals").description("If the stone is touching the ground, you can use an action to summon an earth elemental.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Stone of Good Luck (Luckstone)").description("While this polished agate is on your person, you gain a +1 bonus to ability checks and saving throws.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Talisman of Pure Good").description("This talisman is a mighty symbol of goodness.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Talisman of Ultimate Evil").description("This item symbolizes unrepentant evil.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Talisman of the Sphere").description("When you make an Intelligence (Arcana) check to control a sphere of annihilation, you gain advantage.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Tome of Clear Thought").description("This book contains memory and logic exercises.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Tome of Leadership and Influence").description("This book contains guidelines for influencing and charming others.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Tome of Understanding").description("This book contains intuition and insight exercises.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Universal Solvent").description("This tube holds milky liquid with a strong alcohol smell.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Well of Many Worlds").description("This fine black cloth, soft as silk, is folded up to the dimensions of a handkerchief.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Wind Fan").description("While holding this fan, you can use an action to cast the gust of wind spell from it.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build(),
            EquipmentEntity.builder().name("Wings of Flying").description("While wearing this cloak, you can use an action to speak its command word.").equipmentCategory(EquipmentCategoryEnum.WONDROUS_ITEM).build()
        );
    }

    // ==================== HELPER METHODS ====================
    private Set<AttributeEntity> getAttributeSet(String... names) {
        return Arrays.stream(names)
                .map(name -> attributeCache.get(name))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private String getRaceId(String raceName) {
        RaceEntity raceEntity = raceCache.get(raceName);
        return raceEntity != null ? raceEntity.getId() : null;
    }
}
