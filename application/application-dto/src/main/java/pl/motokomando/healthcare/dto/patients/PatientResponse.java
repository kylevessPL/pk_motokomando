package pl.motokomando.healthcare.dto.patients;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.records.PatientBasicInfoResponse;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientResponse {

    @Schema(description = "Patient basic information")
    private PatientBasicInfoResponse basicInfo;
    @Schema(description = "Patient details")
    private PatientDetailsResponse patientDetails;

}
