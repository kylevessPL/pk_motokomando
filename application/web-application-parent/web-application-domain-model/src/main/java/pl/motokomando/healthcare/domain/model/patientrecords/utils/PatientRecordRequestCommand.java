package pl.motokomando.healthcare.domain.model.patientrecords.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PatientRecordRequestCommand {

    private Integer id;
    private Integer patientId;
    private HealthStatus healthStatus;
    private String notes;
    private LocalDateTime registrationDate;

}
