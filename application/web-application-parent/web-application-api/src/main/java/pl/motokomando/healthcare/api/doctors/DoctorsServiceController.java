package pl.motokomando.healthcare.api.doctors;

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
import pl.motokomando.healthcare.api.doctors.mapper.DoctorsMapper;
import pl.motokomando.healthcare.api.doctors.utils.DoctorPagedQuery;
import pl.motokomando.healthcare.api.doctors.utils.DoctorRequest;
import pl.motokomando.healthcare.api.utils.PageResponse;
import pl.motokomando.healthcare.domain.doctors.DoctorsService;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicPageResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicPagedResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/doctors")
@Tag(name = "Doctors API", description = "API performing operations on doctor resources")
@Validated
@RequiredArgsConstructor
public class DoctorsServiceController {

    private final DoctorsService doctorsService;
    private final DoctorsMapper doctorsMapper;

    @Operation(
            summary = "Get all doctors",
            description = "You can pass additional paging and sorting parameters",
            operationId = "getAllDoctors"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched doctors data",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DoctorBasicPagedResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Doctors data is empty", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<DoctorBasicPagedResponse> getAll(@Valid DoctorPagedQuery query) {
        BasicPagedQueryCommand command = doctorsMapper.mapToCommand(query);
        DoctorBasicPageResponse response = doctorsMapper.mapToResponse(doctorsService.getAllDoctors(command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

    @Operation(
            summary = "Get doctor details by ID",
            description = "You are required to pass doctor ID",
            operationId = "getDoctor"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched doctors details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such doctor with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public DoctorResponse getById(@Parameter(description = "Doctor ID") @PathVariable @Min(value = 1, message = "Doctor ID must be a positive integer value") Integer id) {
        return doctorsMapper.mapToResponse(doctorsService.getDoctor(id));
    }

    @Operation(
            summary = "Register doctor or edit doctor details",
            description = "You are required to pass JSON body with doctor details",
            operationId = "saveDoctor"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully saved doctor details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public void save(@RequestBody @Valid DoctorRequest request) {
        doctorsService.saveDoctor(doctorsMapper.mapToCommand(request));
    }

}
