package pl.motokomando.healthcare.api.patientrecords;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patientrecords.mapper.PatientRecordsMapper;
import pl.motokomando.healthcare.api.patientrecords.utils.PatientRecordRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
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

    @PatchMapping(path = "/id/{id}", consumes = "application/json-patch+json")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody JsonPatch patchDocument) {
        PatientRecordResponse response = patientRecordsMapper.mapToResponse(patientRecordsService.getPatientRecordById(id));
        PatientRecordRequest request = patientRecordsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, PatientRecordRequest.class);
        patientRecordsService.updatePatientRecord(patientRecordsMapper.mapToCommand(request));
    }

}
