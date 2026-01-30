package dm.dracolich.library.web.config.initializer;

import dm.dracolich.library.web.entity.ClassEntity;
import dm.dracolich.library.web.entity.SubclassEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SubclassInitializer {

    public List<SubclassEntity> createSubclasses(Map<String, ClassEntity> classCache) {
        List<SubclassEntity> subclasses = new ArrayList<>();

        // barbarian Subclasses
        subclasses.add(SubclassEntity.builder().name("Berserker").description("Fall into your rage entirely to deliver a tide of powerful blows.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Totem Warrior").description("Your rage comes from the animal spirits of the world who aid.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Ancestral Guardian").description("Your rage is the combination of all your ancestors.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Storm Herald").description("Your rage is second only to that of mother nature, who joins you.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Zealot").description("The rage acts as a gift from the gods. You are their champion.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Beast").description("Gifts from the beasts of this world as you manifest their power.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Wild Soul").description("Your rage is strong enough to shatter the walls of magic.").classId(getClassId(classCache, "barbarian")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Battlerager").description("Giving yourself over to your rage, make your body a weapon.").classId(getClassId(classCache, "barbarian")).custom(false).build());

        // bard Subclasses
        subclasses.add(SubclassEntity.builder().name("College of Lore").description("A way to keep the stories of history and civilizations alive.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Valor").description("Those that tell vibrant and powerful war stories.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Creation").description("Your music and stories shape the very fabric of reality.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Glamor").description("Blessed by the Feywild, your looks are rivaled by none.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Swords").description("Become the best and most elegant sword fighter in the world.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Whispers").description("The hypnotic power of bards can be used for stealth.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Eloquence").description("Words have power; use them to shape any situation.").classId(getClassId(classCache, "bard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Spirits").description("The dead tell stories and have experiences that you can draw on.").classId(getClassId(classCache, "bard")).custom(false).build());

        // cleric Subclasses
        subclasses.add(SubclassEntity.builder().name("Knowledge Domain").description("Serve the idea that knowledge is power, and it must endure.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Life Domain").description("Serve the idea of life; every life and living thing is a wonder.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Light Domain").description("Serve the idea of light; it will burn back the forces of shadow.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Nature Domain").description("Serve the idea of nature; the natural world can't ever fall.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Tempest Domain").description("Serve the idea of change, storms are powerful and resistant").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Trickery Domain").description("Serve the idea of deception, pranks, or more keeps the world moving").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("War Domain").description("Serve the idea of war, whether it's war for honor or power.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Death Domain").description("Serve the idea of death; everything must eventually end.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Twilight Domain").description("Serve the idea of balance; those that attempt to disrupt it must be stopped.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Order Domain").description("Serve the idea of order and law; you are the voice of justice.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Forge Domain").description("Serve the idea of creation; the forges you touch will never fail").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Grave Domain").description("Serve the idea of life and death; the balance must be maintained").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Peace Domain").description("Serve the idea of peace; violence is almost never the answer.").classId(getClassId(classCache, "cleric")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Domain").description("Serve the idea of magic; magic is a power and a wonder").classId(getClassId(classCache, "cleric")).custom(false).build());

        // druid Subclasses
        subclasses.add(SubclassEntity.builder().name("Circle of the Land").description("Grown within a certain biome, your power comes from there.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of the Moon").description("Like a werewolf, your power is based on changing forms.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Dreams").description("The Feywild's nature has blessed you with the power to heal.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Shepherd").description("Like a shepherd, you protect the animals of the world.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Spores").description("Mycelium has many uses and abilities, including raising the dead.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Stars").description("The answer and guidance can always be found in the stars.").classId(getClassId(classCache, "druid")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Wildfire").description("Wildfires bring about change, ecosystems always revive.").classId(getClassId(classCache, "druid")).custom(false).build());

        // fighter Subclasses
        subclasses.add(SubclassEntity.builder().name("Champion").description("Hone your martial ability into an incredibly deadly skill with constant crits.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Battle Master").description("Use the art of war and tactics to gain advantages and command to victory.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Eldritch Knight").description("Learning wizard-like spells to gain the upperhand in any fight.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Archer").description("Mix magic with your arrows to rain literal fire down upon your foes.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Cavalier").description("You will never break; your skill, while mounted or not, can not be beaten.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Samurai").description("Your fighting spirit will allow you to rain a hurricane of deadly blows.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Psi Warrior").description("Blessed with psychic energy, your mind is sharpened just like your weapon.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Rune Knight").description("Learn the ancient power of Giants and their powerful magic runes.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Echo fighter").description("Using magical energy, create an \"echo\" to fight with you.").classId(getClassId(classCache, "fighter")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Purple Dragon Knight").description("A warrior that braves any battle through inspiration.").classId(getClassId(classCache, "fighter")).custom(false).build());

        // monk Subclasses
        subclasses.add(SubclassEntity.builder().name("Way of the Open Hand").description("Use your fists and palms to annihilate your foe.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Shadow").description("Usually called, ninjas and so forth. Their fists strike from the shadow.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Four Elements").description("Control all 4 elements, earth, water, fire and wind in your ways.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of Mercy").description("Your ki and spirit is meant to heal wounds, not create them.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Astral Self").description("Your spirit becomes an entity around you to help you.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Drunken Master").description("Confuse your foes with unpredictable attacks.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Kensei").description("Your weapons become an extension of yourself. A form of art.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Sun Soul").description("Your soul and will is so powerful that it can ignite in fire.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Long Death").description("Your soul and fists become the tools of death.").classId(getClassId(classCache, "monk")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Ascendant Dragon").description("Following the way of dragons, your spirit manifests their power.").classId(getClassId(classCache, "monk")).custom(false).build());

        // Paladin Subclasses
        subclasses.add(SubclassEntity.builder().name("Oath of Devotion").description("Light, Lawful, Honesty, the pure holy warrior.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Ancients").description("Joy and love, the ancients blessed this warrior with nature.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Vengeance").description("Anger and vengeance, something bad pushed this warrior to seek vengeance.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oathbreaker").description("They broke an oath long ago and are cursed for it.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Conquest").description("Destruction and Victory, nothing will stop this warrior.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Redemption").description("A warrior that uses words instead of the sword.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Glory").description("A warrior that is destined for glory, fight for your destiny.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Watchers").description("A warrior sworn to fight anything supernatural.").classId(getClassId(classCache, "paladin")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Crown").description("A warrior that serves a crown or kingdom.").classId(getClassId(classCache, "paladin")).custom(false).build());

        // ranger Subclasses
        subclasses.add(SubclassEntity.builder().name("Fey Wanderer").description("With the power of fey on their side, these rangers charm their foes.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Swarmkeeper").description("Insects, pixies or a similar swarm creature. These rangers take care of them.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Gloom Stalker").description("These hunters wander in the shadows to defeat creatures of the night.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Horizon Walker").description("These hunters seek out creatures from other worlds to end them.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Monster Slayer").description("These warriors hunt down to slay powerful monsters.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Hunter").description("A warrior who hones different parts of their fighting style to hunt.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Beast Master").description("A warrior who has gained the assistance of a spiritual beast.").classId(getClassId(classCache, "ranger")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Drakewarden").description("A warrior who has gained the assistance of a powerful drake that follows them.").classId(getClassId(classCache, "ranger")).custom(false).build());

        // rogue Subclasses
        subclasses.add(SubclassEntity.builder().name("Thief").description("Thieves are rogues who are experts at breaking into things and stealing.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Assassin").description("Assassins are masters at killing while unseen.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Trickster").description("Tricksters mix magic into their skills, enhancing them.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Inquisitive").description("Like a detective, inquisitive rogues watch their targets and use their patterns.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Mastermind").description("These rogues are deadly smart, using their intelligence to fight.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Scout").description("Ambushing and Stealthing with speed, these rogues are always ahead.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Swashbuckler").description("rogues that are fast and light on their feet, impossible to pin.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Phantom").description("Shadow magic is within the hearts of these rogues, stealing souls.").classId(getClassId(classCache, "rogue")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Soulknife").description("Blessed with a psychic mind, their roguish skills are enhanced.").classId(getClassId(classCache, "rogue")).custom(false).build());

        // sorcerer Subclasses
        subclasses.add(SubclassEntity.builder().name("Aberrant Mind").description("Cursed or blessed with a powerful psychic mind, their magic comes from their head.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Clockwork Soul").description("The balance of the universe flows through their veins.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Divine Soul").description("With blood from something Divine, gods watch them with interest.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Shadow Magic").description("Cursed with shadow magic, their power manipulates the shadows.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Storm Sorcery").description("With the blood of a powerful hurricane, storms are a comfort.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Draconic Bloodline").description("With the ancestors of dragons, they show draconic natures.").classId(getClassId(classCache, "sorcerer")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Wild Magic").description("The chaos of the universe flows through their veins.").classId(getClassId(classCache, "sorcerer")).custom(false).build());

        // warlock Subclasses
        subclasses.add(SubclassEntity.builder().name("The Archfey").description("A magical pact with an Archfey of the feywild.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Fiend").description("A magical pact with a demonic or devilish entity.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Great Old One").description("An ancient and powerful unknown entity from worlds beyond.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Celestial").description("A magical pact with an entity of good, law, and order.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Undying").description("A magical pact with an entity that represents the dead.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Hexblade").description("A magical pact with an entity that is or gifted a powerful weapon.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Fathomless").description("A magical pact with a creature of the world's deep oceans.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Genie").description("A magical pact with a powerful elemental.").classId(getClassId(classCache, "warlock")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Undead").description("A magical pact with a powerful deathless being that defies the cycle.").classId(getClassId(classCache, "warlock")).custom(false).build());

        // wizard Subclasses
        subclasses.add(SubclassEntity.builder().name("School of Abjuration").description("Study the defensive powers of magic.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Conjuration").description("Study the ways to control the creatures you summon.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Divination").description("Study the ways magic can show you the future and past.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Enchantment").description("Study the ways that magic can bend the minds of others.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Evocation").description("Study the ways that magic can destroy and offense.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Illusion").description("Study the ways that magic can make the eyes of others lie.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Necromancy").description("Study the forces of life and death.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Transmutation").description("Study the ways magic can shift reality.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Graviturgy").description("Study the ways magic can shift and manipulate gravity.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Chronurgy").description("Study the ways magic can shift and bend time itself.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("War Magic").description("Study the ways magic can be used in combat and how it can be stored.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Bladesinging").description("Study a tradition of wizardry that incorporates swordplay and dance.").classId(getClassId(classCache, "wizard")).custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Order of Scribes").description("Study the ways magic can be enhanced through books of power.").classId(getClassId(classCache, "wizard")).custom(false).build());

        return subclasses;
    }

    private String getClassId(Map<String, ClassEntity> classCache, String className) {
        ClassEntity classEntity = classCache.get(className);
        return classEntity != null ? classEntity.getId() : null;
    }
}
