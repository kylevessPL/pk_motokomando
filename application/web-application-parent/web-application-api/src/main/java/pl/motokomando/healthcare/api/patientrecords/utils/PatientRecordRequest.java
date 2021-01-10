package pl.motokomando.healthcare.api.patientrecords.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.HealthStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientRecordRequest implements Serializable {

    @JsonIgnore
    private Integer id;
    @ApiModelProperty(value = "Patient ID", example = "5")
    @NotNull(message = "Patient ID is mandatory")
    @Min(value = 1, message = "Patient ID must be a positive integer value")
    private Integer patientId;
    @ApiModelProperty(value = "Patient health status")
    @NotNull(message = "Patient health status is mandatory")
    private HealthStatus healthStatus;
    @ApiModelProperty(value = "Health status notes")
    @Size(min = 5, max = 100, message = "Health status notes must be between 5 and 100 characters long")
    private String notes;
    @JsonIgnore
    private LocalDateTime registrationDate;

}
