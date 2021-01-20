package pl.motokomando.healthcare.domain.bills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.bills.utils.BillRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.BILL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BillsServiceImpl implements BillsService {

    private final BillsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Bill getBill(Integer id) {
        return repository.getBillById(id)
                .orElseThrow(() -> new MyException(BILL_NOT_FOUND));
    }

    @Override
    @Transactional
    public Basic createBill(BillRequestCommand command) {
        return repository.createBill(command.getAmount());
    }

    @Override
    @Transactional
    public void updateBill(BillPatchRequestCommand command) {
        repository.updateBill(command);
    }

    @Override
    public void deleteBill(Integer id) {
        boolean deleteResult = repository.deleteBill(id);
        if (!deleteResult) {
            throw new MyException(BILL_NOT_FOUND);
        }
    }

}
