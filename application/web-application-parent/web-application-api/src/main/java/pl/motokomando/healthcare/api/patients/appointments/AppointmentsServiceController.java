package pl.motokomando.healthcare.api.patients.appointments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patients.appointments.mapper.AppointmentsMapper;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPagedQuery;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPatchRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequestParams;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.api.utils.PageResponse;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsService;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicPageResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentResponse;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentBasicPaged;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/patients")
@Validated
@RequiredArgsConstructor
public class AppointmentsServiceController {

    private final AppointmentsService appointmentsService;
    private final AppointmentsMapper appointmentsMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @ApiOperation(
            value = "Get all patient appointments",
            notes = "You can pass additional paging and sorting parameters",
            nickname = "getAllAppointments"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched patient appointments data"),
            @ApiResponse(code = 204, message = "Patient appointments data is empty"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{id}/appointments", produces = APPLICATION_JSON_VALUE)
    public PageResponse<AppointmentBasicPaged> getAll(
            @ApiParam(value = "Patient ID") @PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id,
            @Valid AppointmentPagedQuery query) {
        BasicPagedQueryCommand command = appointmentsMapper.mapToCommand(query);
        AppointmentBasicPageResponse response = appointmentsMapper.mapToResponse(
                appointmentsService.getAllAppointments(id, command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

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

    @ApiOperation(
            value = "Update appointment data",
            notes = "You are required to pass JSON Patch body with patch instructions",
            nickname = "updateAppointment"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated appointment data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{patientId}/appointments/{appointmentId}", consumes = "application/json-patch+json")
    public void update(
            @Valid AppointmentRequestParams params,
            @RequestBody JsonPatch patchDocument) {
        AppointmentRequestParamsCommand paramsCommand = appointmentsMapper.mapToCommand(params);
        AppointmentResponse response = appointmentsMapper.mapToResponse(appointmentsService.getAppointment(paramsCommand));
        AppointmentPatchRequest request = appointmentsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, AppointmentPatchRequest.class);
        AppointmentPatchRequestCommand patchCommand = appointmentsMapper.mapToCommand(response);
        appointmentsMapper.update(request, patchCommand);
        appointmentsService.updateAppointment(patchCommand);
    }

}
