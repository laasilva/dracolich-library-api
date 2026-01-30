package dm.dracolich.library.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dm.dracolich.library.dto.ClassDto;
import dm.dracolich.library.dto.records.ClassResumedRecord;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Builder
public class ClassResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Flux<ClassDto> records;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Flux<ClassResumedRecord> resumedRecords;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Mono<ClassDto> record;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Mono<ClassResumedRecord> resumedRecord;
}
