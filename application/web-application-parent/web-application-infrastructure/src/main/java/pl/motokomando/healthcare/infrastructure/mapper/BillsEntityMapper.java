package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.infrastructure.model.BillsEntity;

import java.util.Optional;

@Component
public class BillsEntityMapper {

    public Optional<Bill> mapToBill(Optional<BillsEntity> billsEntity) {
        return billsEntity.map(this::createBill);
    }

    private Bill createBill(BillsEntity billsEntity) {
        return new Bill(
                billsEntity.getId(),
                billsEntity.getIssueDate().toLocalDateTime(),
                billsEntity.getAmount());
    }

}
