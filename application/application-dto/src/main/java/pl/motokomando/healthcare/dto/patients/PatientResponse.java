package pl.motokomando.healthcare.dto.patients;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasicInfo;
import pl.motokomando.healthcare.dto.patients.utils.PatientDetails;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientResponse {

    @ApiModelProperty(value = "Patient basic information")
    private PatientBasicInfo basicInfo;
    @ApiModelProperty(value = "Patient details")
    private PatientDetails patientDetails;

}
