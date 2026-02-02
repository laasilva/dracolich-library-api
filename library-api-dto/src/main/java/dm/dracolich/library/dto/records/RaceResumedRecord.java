package dm.dracolich.library.dto.records;

import dm.dracolich.library.dto.enums.SizeEnum;

public record RaceResumedRecord(String name,
                                String description,
                                Integer speed,
                                SizeEnum size) {}
