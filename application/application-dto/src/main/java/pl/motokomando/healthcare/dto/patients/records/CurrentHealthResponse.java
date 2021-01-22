package pl.motokomando.healthcare.dto.patients.records;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.records.utils.HealthStatus;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class CurrentHealthResponse {

    private HealthStatus healthStatus;
    private String notes;

}
