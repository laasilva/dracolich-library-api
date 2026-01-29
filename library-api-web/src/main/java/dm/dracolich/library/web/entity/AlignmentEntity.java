package dm.dracolich.library.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlignmentEntity {
    @Id
    private String id;
    private String name;
    private String description;
}
