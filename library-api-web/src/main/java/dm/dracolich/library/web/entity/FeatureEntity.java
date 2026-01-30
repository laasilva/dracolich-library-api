package dm.dracolich.library.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "features")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    /**
     * Maps class name to the level at which this feature is obtained.
     * Examples:
     * - {"Barbarian": 5, "Fighter": 5, "Monk": 5} for Extra Attack
     * - {"Monk": 7, "Rogue": 7} for Evasion
     * - null or empty for universal features like Ability Score Improvement
     */
    private Map<String, Integer> classLevels;
    private boolean custom;
}