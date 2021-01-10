package pl.motokomando.healthcare.infrastructure.bills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.bills.BillsRepository;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.infrastructure.dao.BillsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.BillsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.BillsEntity;

import java.math.BigDecimal;
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
    public void createBill(BigDecimal amount) {
        BillsEntity billsEntity = createEntity(amount);
        dao.save(billsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean billExists(Integer id) {
        return dao.existsById(id);
    }

    private BillsEntity createEntity(BigDecimal amount) {
        BillsEntity billsEntity = new BillsEntity();
        billsEntity.setAmount(amount);
        return  billsEntity;
    }

}
