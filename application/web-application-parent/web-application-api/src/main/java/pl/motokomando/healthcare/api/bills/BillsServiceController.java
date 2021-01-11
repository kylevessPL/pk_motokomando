package pl.motokomando.healthcare.api.bills;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.bills.mapper.BillsMapper;
import pl.motokomando.healthcare.api.bills.utils.BillPatchRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.bills.BillsService;
import pl.motokomando.healthcare.dto.bills.BillBasicResponse;
import pl.motokomando.healthcare.dto.bills.BillResponse;

import javax.json.JsonPatch;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api
@RestController
@RequestMapping("/api/v1/bills")
@Validated
@RequiredArgsConstructor
public class BillsServiceController {

    private final BillsService billsService;
    private final BillsMapper billsMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @ApiOperation(
            value = "Create new bill",
            notes = "You are required to pass bill amount value as parameter",
            nickname = "createBill"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully created bill"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public BillBasicResponse create(
            @RequestParam
            @NotNull(message = "Bill amount value is mandatory")
            @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be a positive decimal number")
            @Digits(integer = 10, fraction = 2, message = "Not a valid amount value")
            BigDecimal amount) {
        return billsMapper.mapToBasicResponse(billsService.createBill(amount));
    }

    @ApiOperation(
            value = "Update bill data",
            notes = "You are required to pass JSON Patch body with patch instructions",
            nickname = "updateBill"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated bill data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/id/{id}", consumes = "application/json-patch+json")
    public void update(@PathVariable Integer id, @RequestBody JsonPatch patchDocument) {
        BillResponse response = billsMapper.mapToResponse(billsService.getBillById(id));
        BillPatchRequest request = billsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, BillPatchRequest.class);
        billsService.updateBill(billsMapper.mapToCommand(request));
    }

}
