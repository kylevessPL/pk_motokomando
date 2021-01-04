package pl.motokomando.healthcare.infrastructure.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasic;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patients.PatientsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PatientsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientsRepositoryImpl implements PatientsRepository {

    private final PatientsEntityDao dao;
    private final PatientsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PatientBasic> getAllPatients(PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = createSortProperty(sortProperties);
        Page<PatientsEntity> result = dao.findAll(PageRequest.of(page, size, sort));
        if (result.hasContent()) {
            return mapper.mapToPatientBasicList(result.getContent());
        }
        return mapper.mapToPatientBasicList(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> getPatientById(Integer id) {
        return mapper.mapToPatient(dao.findById(id));
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand data) {
        PatientsEntity patientsEntity = createEntity(data);
        dao.save(patientsEntity);
    }

    private Sort createSortProperty(SortProperties sortProperties) {
        String sortBy = sortProperties.getSortBy();
        Sort.Direction sortDir = Sort.Direction.valueOf(sortProperties.getSortDir().name());
        if (sortProperties.getSortBy().matches("firstName|lastName")) {
            List<String> values = Arrays.asList("firstName", "lastName");
            return Sort.by(sortDir, values.remove(values.indexOf(sortBy)))
                    .and(Sort.by(sortDir, values.get(0)));
        }
        return Sort.by(sortDir, sortBy);
    }

    private PatientsEntity createEntity(PatientRequestCommand data) {
        PatientsEntity patientsEntity = new PatientsEntity();
        Optional.ofNullable(data.getId()).ifPresent(patientsEntity::setId);
        patientsEntity.setFirstName(data.getFirstName());
        patientsEntity.setLastName(data.getLastName());
        patientsEntity.setBirthDate(data.getBirthDate());
        patientsEntity.setSex(data.getSex());
        patientsEntity.setBloodType(data.getBloodType());
        patientsEntity.setStreetName(data.getStreetName());
        patientsEntity.setHouseNumber(data.getHouseNumber());
        patientsEntity.setZipCode(data.getZipCode());
        patientsEntity.setCity(data.getCity());
        patientsEntity.setDocumentType(data.getDocumentType());
        patientsEntity.setDocumentId(data.getDocumentId());
        patientsEntity.setPhoneNumber(data.getPhoneNumber());
        return patientsEntity;
    }

}
