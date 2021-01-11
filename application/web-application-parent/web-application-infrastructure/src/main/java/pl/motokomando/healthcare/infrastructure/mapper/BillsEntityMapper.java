package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.infrastructure.model.BillsEntity;

import java.util.Optional;

@Component
public class BillsEntityMapper {

    public Optional<Bill> mapToBill(Optional<BillsEntity> billsEntity) {
        return billsEntity.map(this::createBill);
    }

    public BillBasic mapToBillBasic(Integer id) {
        return createBillBasic(id);
    }

    private Bill createBill(BillsEntity billsEntity) {
        return new Bill(
                billsEntity.getId(),
                billsEntity.getIssueDate().toLocalDateTime(),
                billsEntity.getAmount());
    }

    private BillBasic createBillBasic(Integer id) {
        return new BillBasic(id);
    }

}
