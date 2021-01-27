package pl.motokomando.healthcare.infrastructure.doctors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.doctors.DoctorsRepository;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.infrastructure.dao.DoctorsEntityDao;
import pl.motokomando.healthcare.infrastructure.dao.SpecialtiesEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.DoctorsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.DoctorsEntity;
import pl.motokomando.healthcare.infrastructure.model.SpecialtiesEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorsRepositoryImpl implements DoctorsRepository {

    private final DoctorsEntityDao doctorsEntityDao;
    private final SpecialtiesEntityDao specialtiesEntityDao;
    private final DoctorsEntityMapper doctorsEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public DoctorBasicPage getAllDoctors(PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = createSortProperty(sortProperties);
        Page<DoctorsEntity> result = getAllPaged(page, size, sort);
        return doctorsEntityMapper.mapToDoctorBasicPage(
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
    public Doctor getDoctorFullById(Integer id) {
        return doctorsEntityMapper.mapToDoctor(doctorsEntityDao.getOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> getDoctorById(Integer id) {
        return doctorsEntityMapper.mapToDoctor(doctorsEntityDao.findById(id));
    }

    @Override
    public boolean doctorExists(Integer id) {
        return doctorsEntityDao.existsById(id);
    }

    @Override
    @Transactional
    public void saveDoctor(DoctorRequestCommand data) {
        Set<SpecialtiesEntity> specialtiesEntities = specialtiesEntityDao.getAllBySpecialtyIn(data.getSpecialties());
        DoctorsEntity doctorsEntity = createEntity(data, specialtiesEntities);
        doctorsEntityDao.save(doctorsEntity);
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

    private Page<DoctorsEntity> getAllPaged(Integer page, Integer size, Sort sort) {
        Page<DoctorsEntity> result = doctorsEntityDao.findAll(PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = doctorsEntityDao.findAll(PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    private DoctorsEntity createEntity(DoctorRequestCommand data, Set<SpecialtiesEntity> specialtiesEntities) {
        DoctorsEntity doctorsEntity = new DoctorsEntity();
        doctorsEntity.setId(data.getId());
        doctorsEntity.setFirstName(data.getFirstName());
        doctorsEntity.setLastName(data.getLastName());
        doctorsEntity.setSpecialties(specialtiesEntities);
        doctorsEntity.setPhoneNumber(data.getPhoneNumber());
        return doctorsEntity;
    }

}
