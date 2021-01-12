package pl.motokomando.healthcare.infrastructure.bills;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.bills.BillsRepository;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.bills.BillBasic;
import pl.motokomando.healthcare.domain.model.bills.utils.BillPatchRequestCommand;
import pl.motokomando.healthcare.infrastructure.dao.BillsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.BillsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.BillsEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillsRepositoryImpl implements BillsRepository {

    private final BillsEntityDao dao;
    private final BillsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Bill> getBillById(Integer id) {
        return mapper.mapToBill(dao.findById(id));
    }

    @Override
    @Transactional
    public void updateBill(BillPatchRequestCommand data) {
        BillsEntity billsEntity = createEntity(data);
        dao.save(billsEntity);
    }

    @Override
    @Transactional
    public BillBasic createBill(BigDecimal amount) {
        BillsEntity billsEntity = createEntity(amount);
        Integer id = dao.save(billsEntity).getId();
        return mapper.mapToBillBasic(id);
    }

    @Override
    @Transactional
    public boolean deleteBill(Integer id) {
        try {
            dao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean billExists(Integer id) {
        return dao.existsById(id);
    }

    private BillsEntity createEntity(BigDecimal amount) {
        BillsEntity billsEntity = new BillsEntity();
        billsEntity.setAmount(amount);
        return billsEntity;
    }

    private BillsEntity createEntity(BillPatchRequestCommand data) {
        BillsEntity billsEntity = new BillsEntity();
        billsEntity.setId(data.getId());
        billsEntity.setIssueDate(Timestamp.valueOf(data.getIssueDate()));
        billsEntity.setAmount(data.getAmount());
        return billsEntity;
    }

}
