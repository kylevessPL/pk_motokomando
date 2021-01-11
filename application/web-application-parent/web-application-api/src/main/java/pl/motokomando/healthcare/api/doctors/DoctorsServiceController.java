package pl.motokomando.healthcare.api.doctors;

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
import pl.motokomando.healthcare.api.doctors.mapper.DoctorsMapper;
import pl.motokomando.healthcare.api.doctors.utils.DoctorQuery;
import pl.motokomando.healthcare.api.doctors.utils.DoctorRequest;
import pl.motokomando.healthcare.api.utils.PageResponse;
import pl.motokomando.healthcare.domain.doctors.DoctorsService;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorResponse;
import pl.motokomando.healthcare.dto.doctors.utils.DoctorBasic;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/doctors")
@Validated
@RequiredArgsConstructor
public class DoctorsServiceController {

    private final DoctorsService doctorsService;
    private final DoctorsMapper doctorsMapper;

    @ApiOperation(
            value = "Get all doctors",
            notes = "You can pass additional paging and sorting parameters",
            nickname = "getAllDoctors"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched doctors data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<DoctorBasic> getAll(@Valid DoctorQuery query) {
        BasicQueryCommand command = doctorsMapper.mapToCommand(query);
        DoctorBasicResponse response = doctorsMapper.mapToResponse(doctorsService.getAllDoctors(command));
        return new PageResponse<>(
                query.getSize(),
                response.getMeta(),
                response.getContent());
    }

    @ApiOperation(
            value = "Get doctor details by ID",
            notes = "You are required to pass doctor ID",
            nickname = "getDoctorById"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched doctors details"),
            @ApiResponse(code = 400, message = "Parameters not valid or no such doctor with provided ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/id/{id}", produces = APPLICATION_JSON_VALUE)
    public DoctorResponse getById(@PathVariable @Min(value = 1, message = "Doctor ID must be a positive integer value") Integer id) {
        return doctorsMapper.mapToResponse(doctorsService.getDoctorById(id));
    }

    @ApiOperation(
            value = "Register doctor or edit doctor details",
            notes = "You are required to pass JSON body with doctor details",
            nickname = "saveDoctor"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully saved doctor details"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public void save(@RequestBody @Valid DoctorRequest request) {
        doctorsService.saveDoctor(doctorsMapper.mapToCommand(request));
    }

}
