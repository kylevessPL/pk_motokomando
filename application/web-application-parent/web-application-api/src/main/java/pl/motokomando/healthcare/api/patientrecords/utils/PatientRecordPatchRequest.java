package pl.motokomando.healthcare.api.patientrecords.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.HealthStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientRecordPatchRequest implements Serializable {

    @ApiModelProperty(value = "Patient health status")
    @NotNull(message = "Patient health status is mandatory")
    private HealthStatus healthStatus;
    @ApiModelProperty(value = "Health status notes")
    @Size(min = 5, max = 100, message = "Health status notes must be between 5 and 100 characters long")
    private String notes;

}
