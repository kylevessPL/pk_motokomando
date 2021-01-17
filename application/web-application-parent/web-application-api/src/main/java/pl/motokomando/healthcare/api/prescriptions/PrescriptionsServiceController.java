package pl.motokomando.healthcare.api.prescriptions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.prescriptions.mapper.PrescriptionMapper;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionsService;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionBasicResponse;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionResponse;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/prescriptions")
@Validated
@RequiredArgsConstructor
public class PrescriptionsServiceController {

    private final PrescriptionsService prescriptionsService;
    private final PrescriptionMapper prescriptionMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @ApiOperation(
            value = "Create new prescription",
            notes = "You are required to pass JSON body with expiration date and notes (optional)",
            nickname = "createPrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created prescription"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public PrescriptionBasicResponse create(@RequestBody @Valid PrescriptionRequest request) {
        PrescriptionRequestCommand command = prescriptionMapper.mapToCommand(request);
        return prescriptionMapper.mapToBasicResponse(prescriptionsService.createPrescription(command));
    }

    @ApiOperation(
            value = "Update prescription data",
            notes = "You are required to pass JSON Patch body with update instructions",
            nickname = "updatePrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated prescription data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void update(
            @ApiParam(value = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id,
            @RequestBody JsonPatch patchDocument) {
        PrescriptionResponse response = prescriptionMapper.mapToResponse(prescriptionsService.getPrescription(id));
        PrescriptionRequest request = prescriptionMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, PrescriptionRequest.class);
        PrescriptionPatchRequestCommand command = prescriptionMapper.mapToCommand(response);
        prescriptionMapper.update(request, command);
        prescriptionsService.updatePrescription(command);
    }

    @ApiOperation(
            value = "Delete prescription",
            notes = "You are required to pass prescription ID as a parameter",
            nickname = "deletePrescription"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted prescription"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(@ApiParam(value = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id) {
        prescriptionsService.deletePrescription(id);
    }

}
