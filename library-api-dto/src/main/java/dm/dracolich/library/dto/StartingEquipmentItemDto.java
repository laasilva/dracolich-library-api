package dm.dracolich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a starting equipment item with quantity.
 * Used in ClassEntity to track starting equipment options.
 * Equipment is referenced by name to avoid complex object keys in MongoDB.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartingEquipmentItemDto {
    private String equipmentName;
    private Integer quantity;
}
