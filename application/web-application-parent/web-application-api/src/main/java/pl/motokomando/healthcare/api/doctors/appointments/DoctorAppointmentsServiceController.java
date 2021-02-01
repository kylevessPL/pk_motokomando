package pl.motokomando.healthcare.api.doctors.appointments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.doctors.appointments.mapper.DoctorAppointmentsMapper;
import pl.motokomando.healthcare.api.doctors.appointments.utils.DoctorAppointmentQuery;
import pl.motokomando.healthcare.domain.doctors.appointments.DoctorAppointmentsService;
import pl.motokomando.healthcare.domain.model.doctors.appointments.utils.DoctorAppointmentQueryCommand;
import pl.motokomando.healthcare.dto.doctors.appointments.DoctorAppointmentResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/doctors")
@Tag(name = "Doctors API", description = "API performing operations on doctor resources")
@Validated
@RequiredArgsConstructor
public class DoctorAppointmentsServiceController {

    private final DoctorAppointmentsService doctorAppointmentsService;
    private final DoctorAppointmentsMapper doctorAppointmentsMapper;

    @Operation(
            summary = "Get all doctor appointments within date range",
            description = "You are required to pass doctor ID along with start and end date range",
            operationId = "getDoctorAppointments"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched doctor appointments"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such doctor with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}/appointments", produces = APPLICATION_JSON_VALUE)
    public List<DoctorAppointmentResponse> getAppointmentsByIdAndDateRange(
            @Parameter(description = "Doctor ID")
            @Min(value = 1, message = "Doctor ID must be a positive integer value")
            @PathVariable Integer id,
            @ParameterObject @Valid DoctorAppointmentQuery query) {
        DoctorAppointmentQueryCommand command = doctorAppointmentsMapper.mapToCommand(query);
        return doctorAppointmentsMapper.mapToResponse(doctorAppointmentsService.getDoctorAppointments(id, command));
    }

}
