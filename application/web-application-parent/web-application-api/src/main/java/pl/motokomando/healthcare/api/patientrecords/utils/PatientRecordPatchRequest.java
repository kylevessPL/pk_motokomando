package pl.motokomando.healthcare.api.patientrecords.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
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
public class PatientRecordPatchRequest implements Serializable {

    @ApiModelProperty(value = "Medical record ID", example = "1")
    @NotNull(message = "Medical record ID is mandatory")
    @Min(value = 1, message = "Medical record ID must be a positive integer value")
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
    @ApiModelProperty(value = "Patient registration date")
    @NotNull(message = "Patient registration date is mandatory")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime registrationDate;

}
