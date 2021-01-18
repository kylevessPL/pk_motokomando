package pl.motokomando.healthcare.api.patients;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.patients.mapper.PatientsMapper;
import pl.motokomando.healthcare.api.patients.utils.PatientPagedQuery;
import pl.motokomando.healthcare.api.patients.utils.PatientRequest;
import pl.motokomando.healthcare.api.utils.PageResponse;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.domain.patients.PatientsService;
import pl.motokomando.healthcare.dto.patients.PatientBasicPageResponse;
import pl.motokomando.healthcare.dto.patients.PatientResponse;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasicPaged;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patients API", description = "API performing operations on patient resources")
@Validated
@RequiredArgsConstructor
public class PatientsServiceController {

    private final PatientsService patientsService;
    private final PatientsMapper patientsMapper;

    @Operation(
            summary = "Get all patients",
            description = "You can pass additional paging and sorting parameters",
            operationId = "getAllPatients"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched patients data",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PatientBasicPaged.class)))),
            @ApiResponse(responseCode = "204", description = "Patients data is empty", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<PatientBasicPaged> getAll(@Valid PatientPagedQuery query) {
        BasicPagedQueryCommand command = patientsMapper.mapToCommand(query);
        PatientBasicPageResponse response = patientsMapper.mapToResponse(patientsService.getAllPatients(command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

    @Operation(
            summary = "Get patient details by ID",
            description = "You are required to pass patient ID",
            operationId = "getPatient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched patient details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such patient with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public PatientResponse getById(@Parameter(description = "Patient ID") @PathVariable @Min(value = 1, message = "Patient ID must be a positive integer value") Integer id) {
        return patientsMapper.mapToResponse(patientsService.getPatient(id));
    }

    @Operation(
            summary = "Register patient or edit patient details",
            description = "You are required to pass JSON body with patient details",
            operationId = "savePatient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully saved patient details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public void save(@RequestBody @Valid PatientRequest request) {
        patientsService.savePatient(patientsMapper.mapToCommand(request));
    }

}
