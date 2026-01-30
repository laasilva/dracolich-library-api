package dm.dracolich.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubclassDto {
    private String name;
    private String description;
    private String className;
    private Set<AttributeDto> subclassAttributes;
    private boolean custom;
}
