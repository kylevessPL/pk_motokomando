package pl.motokomando.healthcare.api.bills.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.bills.utils.BillRequest;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.dto.bills.BillResponse;

@Mapper
public interface BillsMapper {

    BillResponse mapToResponse(Bill bill);
    BillRequest mapToRequest(BillResponse response);
    BillRequestCommand mapToCommand(BillRequest request);
    BillPatchRequestCommand mapToCommand(BillResponse response);
    void update(BillRequest request, @MappingTarget BillPatchRequestCommand command);

}
