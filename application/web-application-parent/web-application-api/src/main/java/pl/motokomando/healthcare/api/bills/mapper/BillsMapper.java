package pl.motokomando.healthcare.api.bills.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.bills.utils.BillPatchRequest;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.dto.bills.BillBasicResponse;
import pl.motokomando.healthcare.dto.bills.BillResponse;

@Mapper
public interface BillsMapper {

    BillResponse mapToResponse(Bill bill);
    BillBasicResponse mapToBasicResponse(BillBasic billBasic);
    BillPatchRequest mapToRequest(BillResponse response);
    BillPatchRequestCommand mapToCommand(BillPatchRequest request);

}
