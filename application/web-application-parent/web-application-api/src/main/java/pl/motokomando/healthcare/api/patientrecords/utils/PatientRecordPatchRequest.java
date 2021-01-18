package pl.motokomando.healthcare.api.patientrecords.utils;

import com.sun.istack.internal.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.HealthStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientRecordPatchRequest implements Serializable {

    @Schema(description = "Patient health status")
    @NotNull(message = "Patient health status is mandatory")
    private HealthStatus healthStatus;
    @Schema(description = "Health status notes")
    @Size(min = 5, max = 100, message = "Health status notes must be between 5 and 100 characters long")
    @Nullable
    private String notes;

}
