package pl.motokomando.healthcare.api.prescriptions.medicines;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.mapper.BasicMapper;
import pl.motokomando.healthcare.api.prescriptions.medicines.mapper.PrescriptionMedicineMapper;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineBasicRequest;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.api.utils.ResourceCreatedResponse;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineBasicRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.prescriptions.medicines.PrescriptionMedicinesService;
import pl.motokomando.healthcare.dto.prescriptions.medicines.PrescriptionMedicineResponse;
import pl.motokomando.healthcare.dto.utils.BasicResponse;

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
public class PrescriptionMedicinesServiceController {

    private final PrescriptionMedicinesService prescriptionMedicinesService;
    private final PrescriptionMedicineMapper prescriptionMedicineMapper;
    private final BasicMapper basicMapper;

    @Operation(
            summary = "Get prescription medicine details by ID",
            description = "You are required to pass prescription ID and prescription medicine ID",
            operationId = "getPrescriptionMedicine"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched prescription medicine details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such prescription or prescription medicine with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{prescriptionId}/medicines/{prescriptionMedicineId}", produces = APPLICATION_JSON_VALUE)
    public PrescriptionMedicineResponse getById(@Valid PrescriptionMedicineBasicRequest request) {
        PrescriptionMedicineBasicRequestCommand command = prescriptionMedicineMapper.mapToCommand(request);
        return prescriptionMedicineMapper.mapToResponse(prescriptionMedicinesService.getPrescriptionMedicine(command));
    }

    @Operation(
            summary = "Add medicine to prescription",
            description = "You are required to pass JSON body with medicine NDC",
            operationId = "addPrescriptionMedicine"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added medicine to prescription"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}/medicines", produces = APPLICATION_JSON_VALUE)
    public ResourceCreatedResponse create(
            @Parameter(description = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id,
            @RequestBody @Valid PrescriptionMedicineRequest request) {
        PrescriptionMedicineRequestCommand command = prescriptionMedicineMapper.mapToCommand(request);
        BasicResponse response = basicMapper.mapToBasicResponse(prescriptionMedicinesService.createPrescriptionMedicine(id, command));
        return new ResourceCreatedResponse(response.getId());
    }

    @Operation(
            summary = "Remove medicine from prescription",
            description = "You are required to pass prescription and prescription medicine ID's",
            operationId = "removePrescriptionMedicine"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed medicine from prescription"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such prescription or medicine with provided ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{prescriptionId}/medicines/{prescriptionMedicineId}", produces = APPLICATION_JSON_VALUE)
    public void remove(@Valid PrescriptionMedicineBasicRequest request) {
        PrescriptionMedicineBasicRequestCommand command = prescriptionMedicineMapper.mapToCommand(request);
        prescriptionMedicinesService.deletePrescriptionMedicine(command);
    }

}
