package pl.motokomando.healthcare.api.medicines;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/medicines")
@Tag(name = "Medicines API", description = "API searching medicines in FDA database")
@Validated
@RequiredArgsConstructor
public class MedicinesServiceController {

    private final MedicinesService medicinesService;
    private final MedicinesMapper medicinesMapper;

    @Operation(
            summary = "Search medicines",
            description = "You are required to pass query string as a parameter; optional ASC/DESC sorting by medicine name & results limit available",
            operationId = "searchMedicines"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched medicines"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "No medicines found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<MedicineResponse> search(@Valid MedicineQuery query) {
        MedicineCommand command = medicinesMapper.mapToCommand(query);
        return medicinesMapper.mapToResponse(medicinesService.searchMedicine(command));
    }

    @Operation(
            summary = "Find medicine information",
            description = "You are required to pass product NDC as a parameter",
            operationId = "getMedicine"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fecthed medicine details"),
            @ApiResponse(responseCode = "400", description = "Parameter not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medicine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{productNDC}", produces = APPLICATION_JSON_VALUE)
    public MedicineResponse getByProductNDC(
            @Parameter(description = "Product NDC")
            @NotBlank(message = "Product NDC is mandatory")
            @PathVariable String productNDC) {
        return medicinesMapper.mapToResponse(medicinesService.getMedicine(productNDC));
    }

}
