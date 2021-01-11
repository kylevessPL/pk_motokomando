package pl.motokomando.healthcare.domain.bills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.BILL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BillsServiceImpl implements BillsService {

    private final BillsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Bill getBillById(Integer id) {
        return repository.getBillById(id)
                .orElseThrow(() -> new MyException(BILL_NOT_FOUND));
    }

    @Override
    @Transactional
    public BillBasic createBill(BillRequestCommand command) {
        return repository.createBill(command.getAmount());
    }

    @Override
    @Transactional
    public void updateBill(BillPatchRequestCommand command) {
        repository.updateBill(command);
    }

}
