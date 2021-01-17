package pl.motokomando.healthcare.api.prescriptions.medicines;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.prescriptions.medicines.mapper.PrescriptionMedicineMapper;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequest;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.prescriptions.medicines.PrescriptionMedicinesService;

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
public class PrescriptionMedicinesServiceController {

    private final PrescriptionMedicinesService prescriptionMedicinesService;
    private final PrescriptionMedicineMapper prescriptionMedicineMapper;

    @ApiOperation(
            value = "Add medicine to prescription",
            notes = "You are required to pass JSON body with medicine NDC",
            nickname = "create"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added medicine to prescription"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}/medicines", produces = APPLICATION_JSON_VALUE)
    public void create(
            @ApiParam(value = "Prescription ID") @PathVariable @Min(value = 1, message = "Prescription ID must be a positive integer value") Integer id,
            @RequestBody @Valid PrescriptionMedicineRequest request) {
        PrescriptionMedicineRequestCommand command = prescriptionMedicineMapper.mapToCommand(request);
        prescriptionMedicinesService.createPrescriptionMedicine(id, command);
    }

    @ApiOperation(
            value = "Remove medicine from prescription",
            notes = "You are required to pass medicine NDC as a parameter",
            nickname = "remove"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully removed medicine from prescription"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}/medicines", produces = APPLICATION_JSON_VALUE)
    public void remove(@Valid PrescriptionMedicineDeleteRequest request) {
        PrescriptionMedicineDeleteRequestCommand command = prescriptionMedicineMapper.mapToCommand(request);
        prescriptionMedicinesService.deletePrescriptionMedicine(command);
    }

}
