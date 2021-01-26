package pl.motokomando.healthcare.api.bills;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.bills.mapper.BillsMapper;
import pl.motokomando.healthcare.api.bills.utils.BillRequest;
import pl.motokomando.healthcare.api.mapper.BasicMapper;
import pl.motokomando.healthcare.api.utils.JsonPatchHandler;
import pl.motokomando.healthcare.api.utils.ResourceCreatedResponse;
import pl.motokomando.healthcare.domain.bills.BillsService;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.dto.bills.BillResponse;
import pl.motokomando.healthcare.dto.utils.BasicResponse;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/bills")
@Tag(name = "Bills API", description = "API performing operations on bill resources")
@Validated
@RequiredArgsConstructor
public class BillsServiceController {

    private final BillsService billsService;
    private final BillsMapper billsMapper;
    private final BasicMapper basicMapper;
    private final JsonPatchHandler jsonPatchHandler;

    @Operation(
            summary = "Get bill details",
            description = "You are required to pass bill ID",
            operationId = "getBill"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched bills details"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid or no such bill with provided ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public BillResponse getById(
            @Parameter(description = "Bill ID")
            @Min(value = 1, message = "Bill ID must be a positive integer value")
            @PathVariable Integer id) {
        return billsMapper.mapToResponse(billsService.getBill(id));
    }

    @Operation(
            summary = "Create new bill",
            description = "You are required to pass JSON body with bill amount value",
            operationId = "createBill"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created bill", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResourceCreatedResponse create(@RequestBody @Valid BillRequest request) {
        BillRequestCommand command = billsMapper.mapToCommand(request);
        BasicResponse response = basicMapper.mapToBasicResponse(billsService.createBill(command));
        return new ResourceCreatedResponse(response.getId());
    }

    @Operation(
            summary = "Update bill data",
            description = "You are required to pass JSON Patch body with update instructions",
            operationId = "updateBill"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated bill data"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void update(
            @Parameter(description = "Bill ID")
            @Min(value = 1, message = "Bill ID must be a positive integer value")
            @PathVariable Integer id,
            @RequestBody JsonPatch patchDocument) {
        BillResponse response = billsMapper.mapToResponse(billsService.getBill(id));
        BillRequest request = billsMapper.mapToRequest(response);
        request = jsonPatchHandler.patch(patchDocument, request, BillRequest.class);
        BillPatchRequestCommand command = billsMapper.mapToCommand(response);
        billsMapper.update(request, command);
        billsService.updateBill(command);
    }

    @Operation(
            summary = "Delete bill",
            description = "You are required to pass bill ID as a parameter",
            operationId = "deleteBill"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted bill"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void delete(
            @Parameter(description = "Bill ID")
            @Min(value = 1, message = "Bill ID must be a positive integer value")
            @PathVariable Integer id) {
        billsService.deleteBill(id);
    }

}
