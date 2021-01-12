package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;

import java.math.BigDecimal;
import java.util.Optional;

public interface BillsRepository {

    Optional<Bill> getBillById(Integer id);
    void updateBill(BillPatchRequestCommand data);
    BillBasic createBill(BigDecimal amount);
    boolean deleteBill(Integer id);
    boolean billExists(Integer id);

}
