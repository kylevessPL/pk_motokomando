package pl.motokomando.healthcare.api.prescriptions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.mapper.BasicMapper;
import pl.motokomando.healthcare.api.prescriptions.mapper.PrescriptionMapper;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionsService;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionResponse;
import pl.motokomando.healthcare.dto.utils.BasicResponse;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/prescriptions")
@Tag(name = "Prescriptions API", description = "API performing operations on prescription resources")
@Validated
@RequiredArgsConstructor
public class PrescriptionsServiceController {

    private final PrescriptionsService prescriptionsService;
    private final PrescriptionMapper prescriptionMapper;
    private final BasicMapper basicMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @Operation(
            summary = "Get prescription details by ID",
            description = "You are required to pass prescription ID",
            operationId = "getPrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched prescriptions details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such prescription with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public PrescriptionResponse getById(@Parameter(description = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id) {
        return prescriptionMapper.mapToResponse(prescriptionsService.getPrescription(id));
    }

    @Operation(
            summary = "Create new prescription",
            description = "You are required to pass JSON body with expiration date and notes (optional)",
            operationId = "createPrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created prescription"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public BasicResponse create(@RequestBody @Valid PrescriptionRequest request) {
        PrescriptionRequestCommand command = prescriptionMapper.mapToCommand(request);
        return basicMapper.mapToBasicResponse(prescriptionsService.createPrescription(command));
    }

    @Operation(
            summary = "Update prescription data",
            description = "You are required to pass JSON Patch body with update instructions",
            operationId = "updatePrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated prescription data"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void update(
            @Parameter(description = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id,
            @RequestBody JsonPatch patchDocument) {
        PrescriptionResponse response = prescriptionMapper.mapToResponse(prescriptionsService.getPrescription(id));
        PrescriptionRequest request = prescriptionMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, PrescriptionRequest.class);
        PrescriptionPatchRequestCommand command = prescriptionMapper.mapToCommand(response);
        prescriptionMapper.update(request, command);
        prescriptionsService.updatePrescription(command);
    }

    @Operation(
            summary = "Delete prescription",
            description = "You are required to pass prescription ID as a parameter",
            operationId = "deletePrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted prescription"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(@Parameter(description = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id) {
        prescriptionsService.deletePrescription(id);
    }

}
