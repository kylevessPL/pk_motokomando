package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.bills.Bill;

import java.math.BigDecimal;
import java.util.Optional;

public interface BillsRepository {

    Optional<Bill> getBillById(Integer id);
    void createBill(BigDecimal amount);
    boolean billExists(Integer id);

}
