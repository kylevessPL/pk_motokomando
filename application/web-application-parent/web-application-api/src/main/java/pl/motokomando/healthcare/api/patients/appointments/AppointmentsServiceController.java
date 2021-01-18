package pl.motokomando.healthcare.api.patients.appointments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patient appointments API", description = "API performing operations on patient appointment resources")
@Validated
@RequiredArgsConstructor
public class AppointmentsServiceController {

    private final AppointmentsService appointmentsService;
    private final AppointmentsMapper appointmentsMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @Operation(
            summary = "Get all patient appointments",
            description = "You can pass additional paging and sorting parameters",
            operationId = "getAllAppointments"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched patient appointments data",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppointmentBasicPaged.class)))),
            @ApiResponse(responseCode = "204", description = "Patient appointments data is empty", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}/appointments", produces = APPLICATION_JSON_VALUE)
    public PageResponse<AppointmentBasicPaged> getAll(
            @Parameter(description = "Patient ID") @PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id,
            @Valid AppointmentPagedQuery query) {
        BasicPagedQueryCommand command = appointmentsMapper.mapToCommand(query);
        AppointmentBasicPageResponse response = appointmentsMapper.mapToResponse(
                appointmentsService.getAllAppointments(id, command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

    @Operation(
            summary = "Schedule new appointment",
            description = "You are required to pass JSON body with appointment date and doctor ID",
            operationId = "scheduleAppointment"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully scheduled appointment"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}/appointments", produces = APPLICATION_JSON_VALUE)
    public AppointmentBasicResponse create(
            @Parameter(description = "Patient ID") @PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id,
            @RequestBody @Valid AppointmentRequest request) {
        AppointmentRequestCommand command = appointmentsMapper.mapToCommand(request);
        return appointmentsMapper.mapToBasicResponse(appointmentsService.createAppointment(id, command));
    }

    @Operation(
            summary = "Update appointment data",
            description = "You are required to pass JSON Patch body with patch instructions",
            operationId = "updateAppointment"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated appointment data"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
