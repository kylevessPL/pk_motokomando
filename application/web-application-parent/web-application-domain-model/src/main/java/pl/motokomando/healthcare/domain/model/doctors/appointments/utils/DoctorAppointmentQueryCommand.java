package pl.motokomando.healthcare.domain.model.doctors.appointments.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class DoctorAppointmentQueryCommand {

    private LocalDate startDate;
    private LocalDate endDate;

}
