package pl.motokomando.healthcare.domain.doctors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;

import java.util.Optional;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DOCTOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DoctorsServiceImpl implements DoctorsService {

    private final DoctorsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public DoctorBasicPage getAllDoctors(BasicQueryCommand query) {
        PageProperties pageProperties = new PageProperties(query.getPage(), query.getSize());
        SortProperties sortProperties = new SortProperties(query.getSortBy(), query.getSortDir());
        return repository.getAllDoctors(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor getDoctorById(Integer id) {
        return repository.getDoctorById(id)
                .orElseThrow(() -> new MyException(DOCTOR_NOT_FOUND));
    }

    @Override
    @Transactional
    public void saveDoctor(DoctorRequestCommand request) {
        Optional.ofNullable(request.getId()).ifPresent(this::checkDoctorExistence);
        repository.saveDoctor(request);
    }

    private void checkDoctorExistence(Integer id) {
        if (!repository.doctorExists(id)) {
            throw new MyException(DOCTOR_NOT_FOUND);
        }
    }

}
