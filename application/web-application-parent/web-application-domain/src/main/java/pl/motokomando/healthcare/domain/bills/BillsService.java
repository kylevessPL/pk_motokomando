package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;

public interface BillsService {

    Bill getBill(Integer id);
    Basic createBill(BillRequestCommand command);
    void updateBill(BillPatchRequestCommand command);
    void deleteBill(Integer id);

}
