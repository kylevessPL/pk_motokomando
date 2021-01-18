package pl.motokomando.healthcare.dto.patientrecords.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientBasicInfo {

    @Schema(description = "Patient ID", example = "1")
    private Integer patientId;
    @Schema(description = "Patient medical record ID", example = "21")
    private Integer medicalRecordId;
    @Schema(description = "Patient registration date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime registrationDate;

}
