# Dracolich Library API

A reactive REST API for D&D game data, built with Spring Boot WebFlux and MongoDB. This API serves as a comprehensive reference library for classes, subclasses, races, spells, equipment, and other game elements.

## Tech Stack

- **Java 25** with preview features
- **Spring Boot 4.0** with WebFlux (reactive)
- **MongoDB** with Reactive Streams
- **MapStruct** for entity-DTO mapping
- **SpringDoc OpenAPI** for API documentation
- **Lombok** for boilerplate reduction

## Getting Started

### Prerequisites

- Java 25+
- MongoDB running on `localhost:27017`
- Maven 3.9+

### Running the Application

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/dracolich-library/api/v0/`

### Configuration

Environment variables (with defaults):

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `8080` | Server port |
| `MONGODB_URI` | `mongodb://localhost:27017/dracolich-library` | MongoDB connection URI |
| `MONGODB_DATABASE` | `dracolich-library` | Database name |

## API Documentation

Swagger UI is available at: `http://localhost:8080/dracolich-library/api/v0/swagger-ui.html`

### Endpoints

#### Classes

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/classes/all?includeDetails=true` | Fetch all classes (with subclasses when detailed) |
| `GET` | `/classes/{name}?includeDetails=true` | Fetch a class by name |
| `GET` | `/classes/search?name={name}&includeDetails=true` | Search classes by name (partial match) |

**Query Parameters:**
- `includeDetails` (default: `true`) - When `true`, includes subclasses in the response

#### Subclasses

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/subclasses/class/{className}` | Fetch all subclasses for a given class |
| `GET` | `/subclasses/{name}` | Fetch a subclass by name |
| `GET` | `/subclasses/search?name={name}` | Search subclasses by name (partial match) |

#### Spells

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/spells?name={name}` | Fetch a spell by exact name |
| `GET` | `/spells/search?name={name}` | Search spells by name (partial match, case-insensitive) |
| `GET` | `/spells/filter?level={level}&type={type}&school={school}` | Filter spells by level, type, and/or school |

**Query Parameters for `/spells/filter`:**
- `level` (optional) - Spell level (0-9, where 0 = cantrip)
- `type` (optional) - Spell type
- `school` (optional) - Magic school

**Spell Types:** `HEAL`, `ATTACK`, `CONCENTRATION`, `UTILITY`

**Magic Schools:** `ABJURATION`, `CONJURATION`, `DIVINATION`, `ENCHANTMENT`, `EVOCATION`, `ILLUSION`, `NECROMANCY`, `TRANSMUTATION`

**Example Requests:**
```bash
# Get all evocation spells
GET /spells/filter?school=EVOCATION

# Get all 3rd level attack spells
GET /spells/filter?level=3&type=ATTACK

# Search for spells containing "fire"
GET /spells/search?name=fire
```

### Spell Response Model

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | Spell name |
| `description` | string | Full spell description |
| `schoolType` | enum | Magic school (Evocation, Abjuration, etc.) |
| `minSlotLevel` | integer | Minimum spell slot level (0 = cantrip) |
| `castingTime` | integer | Casting time in actions |
| `range` | integer | Range in feet (0 = self/touch) |
| `duration` | integer | Duration in hours (0 = instantaneous) |
| `spellType` | enum | Spell category (Attack, Heal, etc.) |
| `save` | enum | Required saving throw ability |
| `damageTypes` | array | Damage types dealt by the spell |
| `diceType` | enum | Dice used for damage/healing |
| `valueAtSlotLevel` | object | Damage/effect scaling per slot level |

## Data Model

The API provides data for the following game entities:

- **Classes** - All 12 base classes with level progression, proficiencies, and starting equipment
- **Subclasses** - 112 subclasses across all classes
- **Races** - 13 playable races
- **Subraces** - 31 subraces
- **Spells** - 400+ spells organized by level (cantrips through 8th level) with magic school, spell type, damage scaling, and save types
- **Equipment** - 436 items including weapons, armor, gear, and magic items
- **Backgrounds** - 13 character backgrounds
- **Features** - 114 class features
- **Alignments** - 9 alignment options
- **Attributes** - Character abilities and skills

## Data Initialization

On first startup, the application automatically seeds the database with reference data. This includes:

1. **Attributes** - Ability scores (STR, DEX, CON, INT, WIS, CHA) and all skills
2. **Races & Subraces** - Core races with their variants
3. **Classes & Subclasses** - Full class definitions with level progressions
4. **Equipment** - Weapons, armor, adventuring gear, tools, and magic items
5. **Spells** - Complete spell list organized by school and level
6. **Backgrounds** - Character backgrounds with proficiencies and features

The initializer checks if data already exists before seeding, so it only runs on a fresh database.

### Initializer Architecture

Data creation is organized into specialized initializers:
- `ClassInitializer` - Defines all 12 classes with their features, proficiencies, and level progression
- `SubclassInitializer` - Defines subclasses organized by parent class
- `SpellInitializer` - Defines all spells organized by level (cantrips through 9th level) with schools, damage types, and scaling
- `DataInitializer` - Orchestrates the seeding process and handles other entities

## Enum Reference

### Damage Types
`FIRE`, `ACID`, `BLUDGEONING`, `COLD`, `FORCE`, `LIGHTNING`, `NECROTIC`, `PIERCING`, `POISON`, `PSYCHIC`, `RADIANT`, `SLASHING`, `THUNDER`

### Ability Scores (for saving throws)
`STRENGTH`, `DEXTERITY`, `CONSTITUTION`, `INTELLIGENCE`, `WISDOM`, `CHARISMA`

### Dice Types
`D4`, `D6`, `D8`, `D10`, `D12`, `D20`

## Project Structure

```
dracolich-library-api/
├── library-api-dto/          # DTOs, records, and enums
│   └── src/main/java/
│       └── dm/dracolich/library/dto/
│           ├── enums/        # Game enums (abilities, skills, etc.)
│           └── *.java        # DTO classes
│
└── library-api-web/          # Web application module
    └── src/main/java/
        └── dm/dracolich/library/web/
            ├── config/       # Configuration and initializers
            ├── controller/   # REST controllers
            ├── entity/       # MongoDB entities
            ├── mapper/       # MapStruct mappers
            ├── repository/   # Reactive MongoDB repositories
            └── service/      # Business logic
```
