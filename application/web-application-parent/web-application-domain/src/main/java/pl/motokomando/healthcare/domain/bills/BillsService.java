package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;

public interface BillsService {

    Bill getBillById(Integer id);
    BillBasic createBill(BillRequestCommand command);
    void updateBill(BillPatchRequestCommand command);
    void deleteBill(Integer id);

}
