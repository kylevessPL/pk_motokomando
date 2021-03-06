package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;

import java.math.BigDecimal;
import java.util.Optional;

public interface BillsRepository {

    Bill getBillFullById(Integer id);
    Optional<Bill> getBillById(Integer id);
    void updateBill(BillPatchRequestCommand data);
    Basic createBill(BigDecimal amount);
    boolean deleteBill(Integer id);
    boolean billExists(Integer id);

}
