package pl.motokomando.healthcare.api.patients.appointments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patients.appointments.mapper.AppointmentsMapper;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequest;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsService;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/patients")
@Validated
@RequiredArgsConstructor
public class AppointmentsServiceController {

    private final AppointmentsService appointmentsService;
    private final AppointmentsMapper appointmentsMapper;

    @ApiOperation(
            value = "Schedule new appointment",
            notes = "You are required to pass JSON body with appointment date and doctor ID",
            nickname = "scheduleAppointment"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully scheduled appointment"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}/appointments", produces = APPLICATION_JSON_VALUE)
    public AppointmentBasicResponse create(
            @ApiParam(value = "Patient ID") @PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id,
            @RequestBody @Valid AppointmentRequest request) {
        AppointmentRequestCommand command = appointmentsMapper.mapToCommand(request);
        return appointmentsMapper.mapToBasicResponse(appointmentsService.createAppointment(id, command));
    }

}
