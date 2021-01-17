package pl.motokomando.healthcare.domain.model.patients.appointments.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequestParamsCommand implements Serializable {

    private Integer patientId;
    private Integer appointmentId;

}
