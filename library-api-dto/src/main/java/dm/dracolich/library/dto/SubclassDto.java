package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubclassDto {
    private String name;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<AttributeDto> subclassAttributes;
    private boolean custom;
}
