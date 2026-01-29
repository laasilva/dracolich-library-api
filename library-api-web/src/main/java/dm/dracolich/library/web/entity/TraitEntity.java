package dm.dracolich.library.web.entity;

import dm.dracolich.library.dto.Proficiency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "traits")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraitEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private Set<Proficiency> proficiencies; // optional
}
