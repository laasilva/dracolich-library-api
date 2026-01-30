package dm.dracolich.library.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "subclasses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubclassEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String className;
    private Set<AttributeEntity> subclassAttributes;
    private boolean custom;
}
