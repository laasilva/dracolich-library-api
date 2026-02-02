package dm.dracolich.library.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "subraces")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubraceEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String raceName;
    private Map<Integer, List<SpellEntity>> cantripsAndSpells;
}
