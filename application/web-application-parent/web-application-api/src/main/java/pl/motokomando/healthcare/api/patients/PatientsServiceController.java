package pl.motokomando.healthcare.api.patients;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patients.mapper.PatientsMapper;
import pl.motokomando.healthcare.api.patients.utils.PatientQuery;
import pl.motokomando.healthcare.api.patients.utils.PatientRequest;
import pl.motokomando.healthcare.api.utils.PageResponse;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.domain.patients.PatientsService;
import pl.motokomando.healthcare.dto.patients.PatientBasicResponse;
import pl.motokomando.healthcare.dto.patients.PatientResponse;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasic;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/patients")
@Validated
@RequiredArgsConstructor
public class PatientsServiceController {

    private final PatientsService patientsService;
    private final PatientsMapper patientsMapper;

    @ApiOperation(
            value = "Get all patients",
            notes = "You can pass additional paging and sorting parameters",
            nickname = "getAllPatients"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched patients data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<PatientBasic> getAll(@Valid PatientQuery query) {
        BasicQueryCommand command = patientsMapper.mapToCommand(query);
        PatientBasicResponse response = patientsMapper.mapToResponse(patientsService.getAllPatients(command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

    @ApiOperation(
            value = "Get patient details by ID",
            notes = "You are required to pass patient ID",
            nickname = "getPatientById"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched patient details"),
            @ApiResponse(code = 400, message = "Parameters not valid or no such patient with provided ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/id/{id}", produces = APPLICATION_JSON_VALUE)
    public PatientResponse getById(@PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id) {
        return patientsMapper.mapToResponse(patientsService.getPatientById(id));
    }

    @ApiOperation(
            value = "Register patient or edit patient details",
            notes = "You are required to pass JSON body with patient details",
            nickname = "savePatient"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully saved patient details"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public void save(@RequestBody @Valid PatientRequest request) {
        patientsService.savePatient(patientsMapper.mapToCommand(request));
    }

}
