#!/usr/bin/env python3
"""Update SpellInitializer: change castingTime and duration from Integer to String."""
import re

FILE = "library-api-web/src/main/java/dm/dracolich/library/web/config/initializer/SpellInitializer.java"

# Spell-specific overrides: spell_name -> (castingTime, duration)
# Only spells that differ from defaults need to be listed.
# Defaults: castingTime(1)->"Action", castingTime(10)->"10 Minutes", castingTime(60)->"60 Minutes",
#           castingTime(480)->"480 Minutes", castingTime(720)->"720 Minutes", castingTime(1440)->"1440 Minutes"
#           duration(0)->"Instantaneous", duration(1)->"1 Hour", duration(8)->"8 Hours",
#           duration(24)->"24 Hours", duration(240)->"10 Days", duration(720)->"30 Days",
#           duration(7)->"7 Days", duration(6)->"6 Hours"

SPELL_DATA = {
    # === CANTRIPS ===
    # castingTime overrides (Bonus Action / Reaction / 1 Minute)
    "Magic Stone": ("Bonus Action", "1 Minute"),
    "Shillelagh": ("Bonus Action", "1 Minute"),
    "Mending": ("1 Minute", "Instantaneous"),
    # duration overrides for cantrips with duration(0) that shouldn't be Instantaneous
    "Create Bonfire": ("Action", "1 Minute"),
    "Mage Hand": ("Action", "1 Minute"),
    "Produce Flame": ("Action", "10 Minutes"),
    "Guidance": ("Action", "1 Minute"),
    "True Strike": ("Action", "1 Round"),
    "Friends": ("Action", "1 Minute"),
    "Booming Blade": ("Action", "1 Round"),
    "Dancing Lights": ("Action", "1 Minute"),
    "Minor Illusion": ("Action", "1 Minute"),
    "Chill Touch": ("Action", "1 Round"),
    "Blade Ward": ("Action", "1 Round"),
    "Control Flames": ("Action", "1 Hour"),
    "Message": ("Action", "1 Round"),
    "Mold Earth": ("Action", "1 Hour"),
    "Resistance": ("Action", "1 Minute"),
    "Shape Water": ("Action", "1 Hour"),
    "Thaumaturgy": ("Action", "1 Minute"),

    # === LEVEL 1 ===
    # Bonus Action casting times
    "Healing Word": ("Bonus Action", "Instantaneous"),
    "Divine Favor": ("Bonus Action", "1 Minute"),
    "Searing Smite": ("Bonus Action", "1 Minute"),
    "Thunderous Smite": ("Bonus Action", "1 Minute"),
    "Wrathful Smite": ("Bonus Action", "1 Minute"),
    "Expeditious Retreat": ("Bonus Action", "10 Minutes"),
    "Hex": ("Bonus Action", "1 Hour"),
    "Shield of Faith": ("Bonus Action", "10 Minutes"),
    "Compelled Duel": ("Bonus Action", "1 Minute"),
    "Ensnaring Strike": ("Bonus Action", "1 Minute"),
    "Hail of Thorns": ("Bonus Action", "1 Minute"),
    "Hunter's Mark": ("Bonus Action", "1 Hour"),
    # Reaction casting times
    "Shield": ("Reaction", "1 Round"),
    "Absorb Elements": ("Reaction", "1 Round"),
    "Feather Fall": ("Reaction", "1 Minute"),
    "Hellish Rebuke": ("Reaction", "Instantaneous"),
    # 1 Minute casting times
    "Alarm": ("1 Minute", "8 Hours"),
    "Identify": ("1 Minute", "Instantaneous"),
    "Illusory Script": ("1 Minute", "10 Days"),
    # Duration overrides for level 1 spells with duration(0)
    "Guiding Bolt": ("Action", "1 Round"),
    "Witch Bolt": ("Action", "1 Minute"),
    "Faerie Fire": ("Action", "1 Minute"),
    "Entangle": ("Action", "1 Minute"),
    "Grease": ("Action", "1 Minute"),
    "Cause Fear": ("Action", "1 Minute"),
    "Bane": ("Action", "1 Minute"),
    "Bless": ("Action", "1 Minute"),
    "Command": ("Action", "1 Round"),
    "Sleep": ("Action", "1 Minute"),
    "Tasha's Hideous Laughter": ("Action", "1 Minute"),
    "Protection from Evil and Good": ("Action", "10 Minutes"),
    "Sanctuary": ("Action", "1 Minute"),
    "Detect Evil and Good": ("Action", "10 Minutes"),
    "Detect Magic": ("Action", "10 Minutes"),
    "Detect Poison and Disease": ("Action", "10 Minutes"),
    "Speak with Animals": ("Action", "10 Minutes"),
    "Color Spray": ("Action", "1 Round"),
    "Silent Image": ("Action", "10 Minutes"),

    # === LEVEL 2 ===
    # Bonus Action casting times
    "Misty Step": ("Bonus Action", "Instantaneous"),
    "Spiritual Weapon": ("Bonus Action", "1 Minute"),
    "Flame Blade": ("Bonus Action", "10 Minutes"),
    "Shadow Blade": ("Bonus Action", "1 Minute"),
    "Branding Smite": ("Bonus Action", "1 Minute"),
    "Dragon's Breath": ("Bonus Action", "1 Minute"),
    # 1 Minute casting times
    "Magic Mouth": ("1 Minute", "Until Dispelled"),
    "Augury": ("1 Minute", "Instantaneous"),
    # Duration overrides for level 2 with duration(0)
    "Continual Flame": ("Action", "Until Dispelled"),
    "Darkness": ("Action", "10 Minutes"),
    "Gust of Wind": ("Action", "1 Minute"),
    "Moonbeam": ("Action", "1 Minute"),
    "Warding Wind": ("Action", "10 Minutes"),
    "Prayer of Healing": ("10 Minutes", "Instantaneous"),
    "Arcane Lock": ("Action", "Until Dispelled"),
    "Cloud of Daggers": ("Action", "1 Minute"),
    "Dust Devil": ("Action", "1 Minute"),
    "Find Steed": ("10 Minutes", "Instantaneous"),
    "Flaming Sphere": ("Action", "1 Minute"),
    "Detect Thoughts": ("Action", "1 Minute"),
    "Find Traps": ("Action", "Instantaneous"),
    "Locate Animals or Plants": ("Action", "Instantaneous"),
    "Locate Object": ("Action", "10 Minutes"),
    "Calm Emotions": ("Action", "1 Minute"),
    "Crown of Madness": ("Action", "1 Minute"),
    "Enthrall": ("Action", "1 Minute"),
    "Hold Person": ("Action", "1 Minute"),
    "Zone of Truth": ("Action", "10 Minutes"),
    "Blur": ("Action", "1 Minute"),
    "Mirror Image": ("Action", "1 Minute"),
    "Phantasmal Force": ("Action", "1 Minute"),
    "Silence": ("Action", "10 Minutes"),
    "Blindness/Deafness": ("Action", "1 Minute"),
    "Ray of Enfeeblement": ("Action", "1 Minute"),
    "Earthbind": ("Action", "1 Minute"),
    "Enlarge/Reduce": ("Action", "1 Minute"),
    "Heat Metal": ("Action", "1 Minute"),
    "Knock": ("Action", "Instantaneous"),
    "Levitate": ("Action", "10 Minutes"),
    "Maximilian's Earthen Grasp": ("Action", "1 Minute"),
    "Pyrotechnics": ("Action", "Instantaneous"),
    "Spike Growth": ("Action", "10 Minutes"),

    # === LEVEL 4 ===
    "Staggering Smite": ("Bonus Action", "1 Minute"),
    "Banishment": ("Action", "1 Minute"),
    "Evard's Black Tentacles": ("Action", "1 Minute"),
    "Grasping Vine": ("Bonus Action", "1 Minute"),
    "Watery Sphere": ("Action", "1 Minute"),
    "Dimension Door": ("Action", "Instantaneous"),
    "Leomund's Secret Chest": ("Action", "Instantaneous"),
    "Divination": ("Action", "Instantaneous"),
    "Compulsion": ("Action", "1 Minute"),
    "Confusion": ("Action", "1 Minute"),
    "Dominate Beast": ("Action", "1 Minute"),
    "Fire Shield": ("Action", "10 Minutes"),
    "Ice Storm": ("Action", "Instantaneous"),
    "Otiluke's Resilient Sphere": ("Action", "1 Minute"),
    "Sickening Radiance": ("Action", "10 Minutes"),
    "Storm Sphere": ("Action", "1 Minute"),
    "Vitriolic Sphere": ("Action", "Instantaneous"),
    "Wall of Fire": ("Action", "1 Minute"),
    "Greater Invisibility": ("Action", "1 Minute"),
    "Phantasmal Killer": ("Action", "1 Minute"),
    "Blight": ("Action", "Instantaneous"),
    "Shadow of Moil": ("Action", "1 Minute"),
    "Control Water": ("Action", "10 Minutes"),
    "Elemental Bane": ("Action", "1 Minute"),
    "Fabricate": ("10 Minutes", "Instantaneous"),
    "Giant Insect": ("Action", "10 Minutes"),
    "Stone Shape": ("Action", "Instantaneous"),

    # === LEVEL 5 ===
    "Banishing Smite": ("Bonus Action", "1 Minute"),
    "Far Step": ("Bonus Action", "1 Minute"),
    "Holy Weapon": ("Bonus Action", "1 Hour"),
    "Swift Quiver": ("Bonus Action", "1 Minute"),
    "Circle of Power": ("Action", "10 Minutes"),
    "Dispel Evil and Good": ("Action", "1 Minute"),
    "Greater Restoration": ("Action", "Instantaneous"),
    "Cloudkill": ("Action", "10 Minutes"),
    "Conjure Volley": ("Action", "Instantaneous"),
    "Insect Plague": ("Action", "10 Minutes"),
    "Steel Wind Strike": ("Action", "Instantaneous"),
    "Teleportation Circle": ("1 Minute", "1 Round"),
    "Tree Stride": ("Action", "1 Minute"),
    "Commune": ("1 Minute", "1 Minute"),
    "Commune with Nature": ("1 Minute", "Instantaneous"),
    "Contact Other Plane": ("1 Minute", "1 Minute"),
    "Legend Lore": ("10 Minutes", "Instantaneous"),
    "Scrying": ("10 Minutes", "10 Minutes"),
    "Dominate Person": ("Action", "1 Minute"),
    "Hold Monster": ("Action", "1 Minute"),
    "Modify Memory": ("Action", "1 Minute"),
    "Synaptic Static": ("Action", "Instantaneous"),
    "Bigby's Hand": ("Action", "1 Minute"),
    "Cone of Cold": ("Action", "Instantaneous"),
    "Dawn": ("Action", "1 Minute"),
    "Destructive Wave": ("Action", "Instantaneous"),
    "Flame Strike": ("Action", "Instantaneous"),
    "Hallow": ("1440 Minutes", "Until Dispelled"),
    "Immolation": ("Action", "1 Minute"),
    "Maelstrom": ("Action", "1 Minute"),
    "Mass Cure Wounds": ("Action", "Instantaneous"),
    "Wall of Force": ("Action", "10 Minutes"),
    "Wall of Light": ("Action", "10 Minutes"),
    "Wall of Stone": ("Action", "10 Minutes"),
    "Wrath of Nature": ("Action", "1 Minute"),
    "Creation": ("1 Minute", "Special"),
    "Negative Energy Flood": ("Action", "Instantaneous"),
    "Raise Dead": ("60 Minutes", "Instantaneous"),
    "Animate Objects": ("Action", "1 Minute"),
    "Awaken": ("480 Minutes", "Instantaneous"),
    "Reincarnate": ("60 Minutes", "Instantaneous"),
    "Telekinesis": ("Action", "10 Minutes"),
    "Transmute Rock": ("Action", "Instantaneous"),
    "Enervation": ("Action", "1 Minute"),

    # === LEVEL 6 ===
    "Globe of Invulnerability": ("Action", "1 Minute"),
    "Primordial Ward": ("Action", "1 Minute"),
    "Arcane Gate": ("Action", "10 Minutes"),
    "Drawmij's Instant Summons": ("1 Minute", "Until Dispelled"),
    "Heroes' Feast": ("10 Minutes", "Instantaneous"),
    "Planar Ally": ("10 Minutes", "Instantaneous"),
    "Scatter": ("Action", "Instantaneous"),
    "Transport via Plants": ("Action", "1 Round"),
    "Wall of Thorns": ("Action", "10 Minutes"),
    "Word of Recall": ("Action", "Instantaneous"),
    "Otto's Irresistible Dance": ("Action", "1 Minute"),
    "Blade Barrier": ("Action", "10 Minutes"),
    "Chain Lightning": ("Action", "Instantaneous"),
    "Heal": ("Action", "Instantaneous"),
    "Otiluke's Freezing Sphere": ("Action", "Instantaneous"),
    "Sunbeam": ("Action", "1 Minute"),
    "Wall of Ice": ("Action", "10 Minutes"),
    "Mental Prison": ("Action", "1 Minute"),
    "Programmed Illusion": ("Action", "Until Dispelled"),
    "Circle of Death": ("Action", "Instantaneous"),
    "Create Undead": ("1 Minute", "Instantaneous"),
    "Eyebite": ("Action", "1 Minute"),
    "Harm": ("Action", "Instantaneous"),
    "Magic Jar": ("1 Minute", "Until Dispelled"),
    "Bones of the Earth": ("Action", "Instantaneous"),
    "Disintegrate": ("Action", "Instantaneous"),
    "Flesh to Stone": ("Action", "1 Minute"),
    "Investiture of Flame": ("Action", "10 Minutes"),
    "Investiture of Ice": ("Action", "10 Minutes"),
    "Investiture of Stone": ("Action", "10 Minutes"),
    "Investiture of Wind": ("Action", "10 Minutes"),
    "Move Earth": ("Action", "2 Hours"),
    "Tasha's Otherworldly Guise": ("Bonus Action", "1 Minute"),
    "Tenser's Transformation": ("Action", "10 Minutes"),

    # === LEVEL 7 ===
    "Symbol": ("1 Minute", "Until Dispelled"),
    "Plane Shift": ("Action", "Instantaneous"),
    "Teleport": ("Action", "Instantaneous"),
    "Power Word Pain": ("Action", "Instantaneous"),
    "Delayed Blast Fireball": ("Action", "1 Minute"),
    "Divine Word": ("Bonus Action", "Instantaneous"),
    "Fire Storm": ("Action", "Instantaneous"),
    "Mordenkainen's Sword": ("Action", "1 Minute"),
    "Prismatic Spray": ("Action", "Instantaneous"),
    "Whirlwind": ("Action", "1 Minute"),
    "Simulacrum": ("720 Minutes", "Until Dispelled"),
    "Finger of Death": ("Action", "Instantaneous"),
    "Resurrection": ("60 Minutes", "Instantaneous"),
    "Draconic Transformation": ("Bonus Action", "1 Minute"),
    "Reverse Gravity": ("Action", "1 Minute"),
    "Sequester": ("Action", "Until Dispelled"),

    # === LEVEL 8 ===
    "Holy Aura": ("Action", "1 Minute"),
    "Incendiary Cloud": ("Action", "1 Minute"),
    "Maze": ("Action", "10 Minutes"),
    "Tsunami": ("Action", "1 Minute"),
    "Feeblemind": ("Action", "Instantaneous"),
    "Power Word Stun": ("Action", "Instantaneous"),
    "Earthquake": ("Action", "1 Minute"),
    "Maddening Darkness": ("Action", "10 Minutes"),
    "Sunburst": ("Action", "Instantaneous"),
    "Illusory Dragon": ("Action", "1 Minute"),
    "Abi-Dalzim's Horrid Wilting": ("Action", "Instantaneous"),
    "Clone": ("60 Minutes", "Instantaneous"),
    "Animal Shapes": ("Action", "24 Hours"),
}

# Default mappings for bulk replacement
CASTING_TIME_DEFAULTS = {
    1: "Action",
    10: "10 Minutes",
    60: "60 Minutes",
    480: "480 Minutes",
    720: "720 Minutes",
    1440: "1440 Minutes",
}

DURATION_DEFAULTS = {
    0: "Instantaneous",
    1: "1 Hour",
    8: "8 Hours",
    24: "24 Hours",
    240: "10 Days",
    720: "30 Days",
    7: "7 Days",
    6: "6 Hours",
}

with open(FILE, "r") as f:
    content = f.read()

def replace_spell_line(content, spell_name, casting_time, duration):
    """Replace castingTime and duration for a specific spell."""
    # Find the line containing this spell name
    pattern = re.compile(
        r'(\.name\("' + re.escape(spell_name) + r'"\).*?\.castingTime\()(\d+)(\).*?\.duration\()(\d+)(\))'
    )
    match = pattern.search(content)
    if match:
        old = match.group(0)
        new = old
        # Replace castingTime
        new = re.sub(r'\.castingTime\(\d+\)', f'.castingTime("{casting_time}")', new)
        # Replace duration
        new = re.sub(r'\.duration\(\d+\)', f'.duration("{duration}")', new)
        content = content.replace(old, new, 1)
    else:
        print(f"WARNING: Could not find spell: {spell_name}")
    return content

# First, apply spell-specific overrides
for spell_name, (ct, dur) in SPELL_DATA.items():
    content = replace_spell_line(content, spell_name, ct, dur)

# Then, replace remaining integer castingTime/duration with defaults
# Replace castingTime(N) -> castingTime("default") for any remaining integers
for int_val, str_val in CASTING_TIME_DEFAULTS.items():
    content = re.sub(
        rf'\.castingTime\({int_val}\)',
        f'.castingTime("{str_val}")',
        content
    )

for int_val, str_val in DURATION_DEFAULTS.items():
    content = re.sub(
        rf'\.duration\({int_val}\)',
        f'.duration("{str_val}")',
        content
    )

with open(FILE, "w") as f:
    f.write(content)

# Verify no integer castingTime/duration remain
remaining_ct = re.findall(r'\.castingTime\(\d+\)', content)
remaining_dur = re.findall(r'\.duration\(\d+\)', content)
if remaining_ct:
    print(f"WARNING: Remaining integer castingTime: {remaining_ct}")
if remaining_dur:
    print(f"WARNING: Remaining integer duration: {remaining_dur}")
if not remaining_ct and not remaining_dur:
    print("SUCCESS: All castingTime and duration values converted to strings.")

# Count spells processed
spell_count = len(re.findall(r'\.castingTime\("', content))
print(f"Total spells with string castingTime: {spell_count}")
