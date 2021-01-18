package pl.motokomando.healthcare.dto.patients;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.dto.patients.utils.PatientDetails;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientResponse {

    @Schema(description = "Patient basic information")
    private PatientBasicInfo basicInfo;
    @Schema(description = "Patient details")
    private PatientDetails patientDetails;

}
