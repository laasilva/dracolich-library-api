package dm.dracolich.library.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "backgrounds")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private String featureId;  // reference to FeatureEntity
    private boolean custom;
}
