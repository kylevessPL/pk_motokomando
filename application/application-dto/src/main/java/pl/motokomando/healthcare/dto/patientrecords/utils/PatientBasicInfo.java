package pl.motokomando.healthcare.dto.patientrecords.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientBasicInfo {

    @ApiModelProperty(value = "Patient ID", example = "1")
    private Integer patientId;
    @ApiModelProperty(value = "Patient medical record ID", example = "21")
    private Integer medicalRecordId;
    @ApiModelProperty(value = "Patient registration date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime registrationDate;

}
