package pl.motokomando.healthcare.api.doctors.appointments.utils;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@NoArgsConstructor
@Getter
@Setter
public class DoctorAppointmentQuery {

    @Parameter(description = "Filter start date", required = true)
    @Future(message = "Start date must be in the future")
    @NotNull(message = "Start date is mandatory")
    @DateTimeFormat(iso = DATE)
    private LocalDate startDate;
    @Parameter(description = "Filter end date", required = true)
    @Future(message = "End date must be in the future")
    @NotNull(message = "End date is mandatory")
    @DateTimeFormat(iso = DATE)
    private LocalDate endDate;

}
