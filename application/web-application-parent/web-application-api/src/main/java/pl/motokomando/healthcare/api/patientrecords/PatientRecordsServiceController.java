package pl.motokomando.healthcare.api.patientrecords;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patientrecords.mapper.PatientRecordsMapper;
import pl.motokomando.healthcare.api.patientrecords.utils.PatientRecordPatchRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.patientrecords.PatientRecordsService;
import pl.motokomando.healthcare.dto.patientrecords.PatientRecordResponse;

import javax.json.JsonPatch;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api
@RestController
@RequestMapping("/api/v1/patientrecords")
@Validated
@RequiredArgsConstructor
public class PatientRecordsServiceController {

    private final PatientRecordsService patientRecordsService;
    private final PatientRecordsMapper patientRecordsMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @ApiOperation(
            value = "Update patient record data",
            notes = "You are required to pass JSON Patch body with patch instructions",
            nickname = "updatePatientRecord"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated patient record data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void update(@ApiParam(value = "Patient record ID") @PathVariable Integer id, @RequestBody JsonPatch patchDocument) {
        PatientRecordResponse response = patientRecordsMapper.mapToResponse(patientRecordsService.getPatientRecordById(id));
        PatientRecordPatchRequest request = patientRecordsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, PatientRecordPatchRequest.class);
        PatientRecordPatchRequestCommand command = patientRecordsMapper.mapToCommand(response);
        patientRecordsMapper.update(request, command);
        patientRecordsService.updatePatientRecord(command);
    }

}
