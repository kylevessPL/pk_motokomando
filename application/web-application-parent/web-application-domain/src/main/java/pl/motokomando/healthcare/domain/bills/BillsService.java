package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;

import java.math.BigDecimal;

public interface BillsService {

    Bill getBillById(Integer id);
    void updateBill(BillPatchRequestCommand command);
    BillBasic createBill(BigDecimal amount);

}
