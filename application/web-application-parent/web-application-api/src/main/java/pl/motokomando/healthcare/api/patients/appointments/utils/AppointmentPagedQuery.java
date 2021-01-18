package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.api.utils.ValidateString;
import pl.motokomando.healthcare.domain.model.utils.SortDirection;

import javax.validation.constraints.Min;

import static pl.motokomando.healthcare.domain.model.utils.SortDirection.ASC;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentPagedQuery {

    @Parameter(description = "Page number, default: 1", example = "5")
    @Min(value = 1, message = "Page number must be a positive integer value")
    private Integer page = 1;
    @Parameter(description = "Page size, default: 10", example = "50")
    @Min(value = 1, message = "Page size must be a positive integer value")
    private Integer size = 20;
    @Parameter(description = "Value to sort by, default: id, allowable values: id, appointmentDate, appointmentStatus", example = "appointmentDate")
    @ValidateString(acceptedValues = {"id", "appointmentDate", "appointmentStatus"}, message = "Sort by property not valid")
    private String sortBy = "id";
    @Parameter(description = "Sort direction, default: ASC, allowable values: ASC, DESC")
    private SortDirection sortDir = ASC;

}
