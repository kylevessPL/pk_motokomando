package pl.motokomando.healthcare.api.bills;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.bills.mapper.BillsMapper;
import pl.motokomando.healthcare.api.bills.utils.BillRequest;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.domain.bills.BillsService;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.dto.bills.BillBasicResponse;
import pl.motokomando.healthcare.dto.bills.BillResponse;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.Min;

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
            notes = "You are required to pass JSON body with bill amount value",
            nickname = "createBill"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created bill"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public BillBasicResponse create(@RequestBody @Valid BillRequest request) {
        BillRequestCommand command = billsMapper.mapToCommand(request);
        return billsMapper.mapToBasicResponse(billsService.createBill(command));
    }

    @ApiOperation(
            value = "Update bill data",
            notes = "You are required to pass JSON Patch body with update instructions",
            nickname = "updateBill"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated bill data"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void update(
            @ApiParam(value = "Bill ID") @PathVariable @Min(value = 1, message = "Bill ID must be a positive integer value") Integer id,
            @RequestBody JsonPatch patchDocument) {
        BillResponse response = billsMapper.mapToResponse(billsService.getBill(id));
        BillRequest request = billsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, BillRequest.class);
        BillPatchRequestCommand command = billsMapper.mapToCommand(response);
        billsMapper.update(request, command);
        billsService.updateBill(command);
    }

    @ApiOperation(
            value = "Delete bill",
            notes = "You are required to pass bill ID as a parameter",
            nickname = "deleteBill"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted bill"),
            @ApiResponse(code = 400, message = "Parameters not valid"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(@ApiParam(value = "Bill ID") @PathVariable @Min(value = 1, message = "Bill ID must be a positive integer value") Integer id) {
        billsService.deleteBill(id);
    }

}
