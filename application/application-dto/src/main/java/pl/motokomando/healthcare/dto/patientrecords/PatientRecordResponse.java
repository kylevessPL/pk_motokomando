package pl.motokomando.healthcare.dto.patientrecords;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patientrecords.utils.HealthStatus;

import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientRecordResponse {

    @ApiModelProperty(value = "Medical record ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Patient ID", example = "5")
    private Integer patientId;
    @ApiModelProperty(value = "Patient health status")
    private HealthStatus healthStatus;
    @ApiModelProperty(value = "Health status notes")
    private String notes;
    @ApiModelProperty(value = "Patient registration date")
    private LocalDateTime registrationDate;

}
