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

**Query Parameters:**
- `includeDetails` (default: `true`) - When `true`, includes subclasses in the response

#### Subclasses

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/subclasses/class/{className}` | Fetch all subclasses for a given class |
| `GET` | `/subclasses/{name}` | Fetch a subclass by name |

## Data Model

The API provides data for the following game entities:

- **Classes** - All 12 base classes with level progression, proficiencies, and starting equipment
- **Subclasses** - 112 subclasses across all classes
- **Races** - 13 playable races
- **Subraces** - 31 subraces
- **Spells** - 464 spells with school, level, and class associations
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
- `DataInitializer` - Orchestrates the seeding process and handles other entities

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
