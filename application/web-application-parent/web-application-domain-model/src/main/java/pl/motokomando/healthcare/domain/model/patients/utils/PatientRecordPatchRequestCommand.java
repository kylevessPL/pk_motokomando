package pl.motokomando.healthcare.domain.model.patients.utils;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PatientRecordPatchRequestCommand {

    private Integer id;
    private Integer patientId;
    private HealthStatus healthStatus;
    @Nullable
    private String notes;
    private LocalDateTime registrationDate;

}
