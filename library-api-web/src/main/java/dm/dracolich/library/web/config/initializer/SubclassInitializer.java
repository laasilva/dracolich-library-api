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

        // barbarian
        subclasses.add(SubclassEntity.builder().name("Berserker").description("Fall into your rage entirely to deliver a tide of powerful blows.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Totem Warrior").description("Your rage comes from the animal spirits of the world who aid.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Ancestral Guardian").description("Your rage is the combination of all your ancestors.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Storm Herald").description("Your rage is second only to that of mother nature, who joins you.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Zealot").description("The rage acts as a gift from the gods. You are their champion.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Beast").description("Gifts from the beasts of this world as you manifest their power.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Wild Soul").description("Your rage is strong enough to shatter the walls of magic.").className("barbarian").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Battlerager").description("Giving yourself over to your rage, make your body a weapon.").className("barbarian").custom(false).build());

        // bard
        subclasses.add(SubclassEntity.builder().name("College of Lore").description("A way to keep the stories of history and civilizations alive.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Valor").description("Those that tell vibrant and powerful war stories.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Creation").description("Your music and stories shape the very fabric of reality.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Glamor").description("Blessed by the Feywild, your looks are rivaled by none.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Swords").description("Become the best and most elegant sword fighter in the world.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Whispers").description("The hypnotic power of bards can be used for stealth.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Eloquence").description("Words have power; use them to shape any situation.").className("bard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("College of Spirits").description("The dead tell stories and have experiences that you can draw on.").className("bard").custom(false).build());

        // cleric
        subclasses.add(SubclassEntity.builder().name("Knowledge Domain").description("Serve the idea that knowledge is power, and it must endure.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Life Domain").description("Serve the idea of life; every life and living thing is a wonder.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Light Domain").description("Serve the idea of light; it will burn back the forces of shadow.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Nature Domain").description("Serve the idea of nature; the natural world can't ever fall.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Tempest Domain").description("Serve the idea of change, storms are powerful and resistant").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Trickery Domain").description("Serve the idea of deception, pranks, or more keeps the world moving").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("War Domain").description("Serve the idea of war, whether it's war for honor or power.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Death Domain").description("Serve the idea of death; everything must eventually end.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Twilight Domain").description("Serve the idea of balance; those that attempt to disrupt it must be stopped.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Order Domain").description("Serve the idea of order and law; you are the voice of justice.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Forge Domain").description("Serve the idea of creation; the forges you touch will never fail").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Grave Domain").description("Serve the idea of life and death; the balance must be maintained").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Peace Domain").description("Serve the idea of peace; violence is almost never the answer.").className("cleric").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Domain").description("Serve the idea of magic; magic is a power and a wonder").className("cleric").custom(false).build());

        // druid
        subclasses.add(SubclassEntity.builder().name("Circle of the Land").description("Grown within a certain biome, your power comes from there.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of the Moon").description("Like a werewolf, your power is based on changing forms.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Dreams").description("The Feywild's nature has blessed you with the power to heal.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Shepherd").description("Like a shepherd, you protect the animals of the world.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Spores").description("Mycelium has many uses and abilities, including raising the dead.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Stars").description("The answer and guidance can always be found in the stars.").className("druid").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Circle of Wildfire").description("Wildfires bring about change, ecosystems always revive.").className("druid").custom(false).build());

        // fighter
        subclasses.add(SubclassEntity.builder().name("Champion").description("Hone your martial ability into an incredibly deadly skill with constant crits.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Battle Master").description("Use the art of war and tactics to gain advantages and command to victory.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Eldritch Knight").description("Learning wizard-like spells to gain the upperhand in any fight.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Archer").description("Mix magic with your arrows to rain literal fire down upon your foes.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Cavalier").description("You will never break; your skill, while mounted or not, can not be beaten.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Samurai").description("Your fighting spirit will allow you to rain a hurricane of deadly blows.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Psi Warrior").description("Blessed with psychic energy, your mind is sharpened just like your weapon.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Rune Knight").description("Learn the ancient power of Giants and their powerful magic runes.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Echo fighter").description("Using magical energy, create an \"echo\" to fight with you.").className("fighter").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Purple Dragon Knight").description("A warrior that braves any battle through inspiration.").className("fighter").custom(false).build());

        // monk
        subclasses.add(SubclassEntity.builder().name("Way of the Open Hand").description("Use your fists and palms to annihilate your foe.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Shadow").description("Usually called, ninjas and so forth. Their fists strike from the shadow.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Four Elements").description("Control all 4 elements, earth, water, fire and wind in your ways.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of Mercy").description("Your ki and spirit is meant to heal wounds, not create them.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Astral Self").description("Your spirit becomes an entity around you to help you.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Drunken Master").description("Confuse your foes with unpredictable attacks.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Kensei").description("Your weapons become an extension of yourself. A form of art.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Sun Soul").description("Your soul and will is so powerful that it can ignite in fire.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Long Death").description("Your soul and fists become the tools of death.").className("monk").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Way of the Ascendant Dragon").description("Following the way of dragons, your spirit manifests their power.").className("monk").custom(false).build());

        // Paladin
        subclasses.add(SubclassEntity.builder().name("Oath of Devotion").description("Light, Lawful, Honesty, the pure holy warrior.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Ancients").description("Joy and love, the ancients blessed this warrior with nature.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Vengeance").description("Anger and vengeance, something bad pushed this warrior to seek vengeance.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oathbreaker").description("They broke an oath long ago and are cursed for it.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Conquest").description("Destruction and Victory, nothing will stop this warrior.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Redemption").description("A warrior that uses words instead of the sword.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of Glory").description("A warrior that is destined for glory, fight for your destiny.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Watchers").description("A warrior sworn to fight anything supernatural.").className("paladin").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Oath of the Crown").description("A warrior that serves a crown or kingdom.").className("paladin").custom(false).build());

        // ranger
        subclasses.add(SubclassEntity.builder().name("Fey Wanderer").description("With the power of fey on their side, these rangers charm their foes.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Swarmkeeper").description("Insects, pixies or a similar swarm creature. These rangers take care of them.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Gloom Stalker").description("These hunters wander in the shadows to defeat creatures of the night.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Horizon Walker").description("These hunters seek out creatures from other worlds to end them.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Monster Slayer").description("These warriors hunt down to slay powerful monsters.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Hunter").description("A warrior who hones different parts of their fighting style to hunt.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Beast Master").description("A warrior who has gained the assistance of a spiritual beast.").className("ranger").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Drakewarden").description("A warrior who has gained the assistance of a powerful drake that follows them.").className("ranger").custom(false).build());

        // rogue
        subclasses.add(SubclassEntity.builder().name("Thief").description("Thieves are rogues who are experts at breaking into things and stealing.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Assassin").description("Assassins are masters at killing while unseen.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Arcane Trickster").description("Tricksters mix magic into their skills, enhancing them.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Inquisitive").description("Like a detective, inquisitive rogues watch their targets and use their patterns.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Mastermind").description("These rogues are deadly smart, using their intelligence to fight.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Scout").description("Ambushing and Stealthing with speed, these rogues are always ahead.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Swashbuckler").description("rogues that are fast and light on their feet, impossible to pin.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Phantom").description("Shadow magic is within the hearts of these rogues, stealing souls.").className("rogue").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Soulknife").description("Blessed with a psychic mind, their roguish skills are enhanced.").className("rogue").custom(false).build());

        // sorcerer
        subclasses.add(SubclassEntity.builder().name("Aberrant Mind").description("Cursed or blessed with a powerful psychic mind, their magic comes from their head.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Clockwork Soul").description("The balance of the universe flows through their veins.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Divine Soul").description("With blood from something Divine, gods watch them with interest.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Shadow Magic").description("Cursed with shadow magic, their power manipulates the shadows.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Storm Sorcery").description("With the blood of a powerful hurricane, storms are a comfort.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Draconic Bloodline").description("With the ancestors of dragons, they show draconic natures.").className("sorcerer").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Wild Magic").description("The chaos of the universe flows through their veins.").className("sorcerer").custom(false).build());

        // warlock
        subclasses.add(SubclassEntity.builder().name("The Archfey").description("A magical pact with an Archfey of the feywild.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Fiend").description("A magical pact with a demonic or devilish entity.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Great Old One").description("An ancient and powerful unknown entity from worlds beyond.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Celestial").description("A magical pact with an entity of good, law, and order.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Undying").description("A magical pact with an entity that represents the dead.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Hexblade").description("A magical pact with an entity that is or gifted a powerful weapon.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Fathomless").description("A magical pact with a creature of the world's deep oceans.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Genie").description("A magical pact with a powerful elemental.").className("warlock").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("The Undead").description("A magical pact with a powerful deathless being that defies the cycle.").className("warlock").custom(false).build());

        // wizard
        subclasses.add(SubclassEntity.builder().name("School of Abjuration").description("Study the defensive powers of magic.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Conjuration").description("Study the ways to control the creatures you summon.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Divination").description("Study the ways magic can show you the future and past.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Enchantment").description("Study the ways that magic can bend the minds of others.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Evocation").description("Study the ways that magic can destroy and offense.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Illusion").description("Study the ways that magic can make the eyes of others lie.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Necromancy").description("Study the forces of life and death.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Transmutation").description("Study the ways magic can shift reality.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Graviturgy").description("Study the ways magic can shift and manipulate gravity.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("School of Chronurgy").description("Study the ways magic can shift and bend time itself.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("War Magic").description("Study the ways magic can be used in combat and how it can be stored.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Bladesinging").description("Study a tradition of wizardry that incorporates swordplay and dance.").className("wizard").custom(false).build());
        subclasses.add(SubclassEntity.builder().name("Order of Scribes").description("Study the ways magic can be enhanced through books of power.").className("wizard").custom(false).build());

        return subclasses;
    }
}
