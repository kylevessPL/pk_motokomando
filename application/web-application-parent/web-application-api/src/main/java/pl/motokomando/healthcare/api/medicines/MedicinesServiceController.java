package pl.motokomando.healthcare.api.medicines;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.medicines.mapper.MedicinesMapper;
import pl.motokomando.healthcare.api.medicines.utils.MedicineQuery;
import pl.motokomando.healthcare.domain.medicines.MedicinesService;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;
import pl.motokomando.healthcare.dto.medicines.MedicineResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/medicines")
@Validated
@RequiredArgsConstructor
public class MedicinesServiceController {

    private final MedicinesService medicinesService;
    private final MedicinesMapper medicinesMapper;

    @ApiOperation(
            value = "Search medicines by query string",
            notes = "You are required to pass query string as a parameter; optional ASC/DESC sorting by medicine name & results limit available",
            nickname = "searchMedicines"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched medicines"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 404, message = "No medicines found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<MedicineResponse> search(@Valid MedicineQuery query) {
        MedicineCommand command = medicinesMapper.mapToCommand(query);
        return medicinesMapper.mapToResponse(medicinesService.searchMedicine(command));
    }

    @ApiOperation(
            value = "Find medicine by FDA product NDC",
            notes = "You are required to pass product NDC as a parameter",
            nickname = "getMedicine"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fecthed medicine details"),
            @ApiResponse(code = 400, message = "Parameter not valid"),
            @ApiResponse(code = 404, message = "Medicine not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{productNDC}", produces = APPLICATION_JSON_VALUE)
    public MedicineResponse getByProductNDC(@ApiParam(value = "Product NDC") @PathVariable @NotBlank(message = "Product NDC is mandatory") String productNDC) {
        return medicinesMapper.mapToResponse(medicinesService.getMedicine(productNDC));
    }

}
