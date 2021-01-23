package pl.motokomando.healthcare.api.patients.records;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patients.records.mapper.PatientRecordsMapper;
import pl.motokomando.healthcare.api.patients.records.utils.PatientRecordPatchRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.patients.records.PatientRecordsService;
import pl.motokomando.healthcare.dto.patients.records.PatientRecordResponse;

import javax.json.JsonPatch;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patients API", description = "API performing operations on patient resources")
@Validated
@RequiredArgsConstructor
public class PatientRecordsServiceController {

    private final PatientRecordsService patientRecordsService;
    private final PatientRecordsMapper patientRecordsMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @Operation(
            summary = "Update patient record data",
            description = "You are required to pass JSON Patch body with patch instructions",
            operationId = "updatePatientRecord"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated patient record data"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}/record", consumes = "application/json-patch+json")
    public void update(
            @Parameter(description = "Patient ID")
            @Min(value = 1, message = "Patient ID must be a positive integer value")
            @PathVariable Integer id,
            @RequestBody JsonPatch patchDocument) {
        PatientRecordResponse response = patientRecordsMapper.mapToResponse(patientRecordsService.getPatientRecord(id));
        PatientRecordPatchRequest request = patientRecordsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, PatientRecordPatchRequest.class);
        PatientRecordPatchRequestCommand command = patientRecordsMapper.mapToCommand(response);
        patientRecordsMapper.update(request, command);
        patientRecordsService.updatePatientRecord(command);
    }

}
