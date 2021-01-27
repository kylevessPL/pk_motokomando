package pl.motokomando.healthcare.infrastructure.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patients.PatientsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PatientsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientsRepositoryImpl implements PatientsRepository {

    private final PatientsEntityDao dao;
    private final PatientsEntityMapper patientsEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public PatientBasicPage getAllPatients(PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = createSortProperty(sortProperties);
        Page<PatientsEntity> result = getAllPaged(page, size, sort);
        return patientsEntityMapper.mapToPatientBasicPage(
                result.hasContent() ? result.getContent() : Collections.emptyList(),
                result.isFirst(),
                result.isLast(),
                result.hasPrevious(),
                result.hasNext(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDetails> getPatientById(Integer id) {
        return patientsEntityMapper.mapToPatient(dao.findById(id));
    }

    @Override
    public boolean patientExists(Integer id) {
        return dao.existsById(id);
    }

    @Override
    @Transactional
    public Integer savePatient(PatientRequestCommand data) {
        PatientsEntity patientsEntity = createEntity(data);
        return dao.save(patientsEntity).getId();
    }

    private Sort createSortProperty(SortProperties sortProperties) {
        String sortBy = sortProperties.getSortBy();
        Sort.Direction sortDir = Sort.Direction.valueOf(sortProperties.getSortDir().name());
        if (sortProperties.getSortBy().matches("firstName|lastName")) {
            List<String> values = new ArrayList<>(Arrays.asList("firstName", "lastName"));
            return Sort.by(sortDir, values.remove(values.indexOf(sortBy)))
                    .and(Sort.by(sortDir, values.get(0)));
        }
        return Sort.by(sortDir, sortBy);
    }

    private Page<PatientsEntity> getAllPaged(Integer page, Integer size, Sort sort) {
        Page<PatientsEntity> result = dao.findAll(PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = dao.findAll(PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    private PatientsEntity createEntity(PatientRequestCommand data) {
        PatientsEntity patientsEntity = new PatientsEntity();
        patientsEntity.setId(data.getId());
        patientsEntity.setFirstName(data.getFirstName());
        patientsEntity.setLastName(data.getLastName());
        patientsEntity.setBirthDate(data.getBirthDate());
        patientsEntity.setSex(data.getSex());
        patientsEntity.setBloodType(data.getBloodType());
        patientsEntity.setStreetName(data.getStreetName());
        patientsEntity.setHouseNumber(data.getHouseNumber());
        patientsEntity.setZipCode(data.getZipCode());
        patientsEntity.setCity(data.getCity());
        patientsEntity.setPesel(data.getPesel());
        patientsEntity.setPhoneNumber(data.getPhoneNumber());
        return patientsEntity;
    }

}
