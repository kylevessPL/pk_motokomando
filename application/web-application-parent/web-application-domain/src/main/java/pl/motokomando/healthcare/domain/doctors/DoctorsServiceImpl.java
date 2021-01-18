package pl.motokomando.healthcare.domain.doctors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
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
    public DoctorBasicPage getAllDoctors(BasicPagedQueryCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        return repository.getAllDoctors(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor getDoctor(Integer id) {
        return repository.getDoctorById(id)
                .orElseThrow(() -> new MyException(DOCTOR_NOT_FOUND));
    }

    @Override
    @Transactional
    public void saveDoctor(DoctorRequestCommand command) {
        Optional.ofNullable(command.getId()).ifPresent(this::checkDoctorExistence);
        repository.saveDoctor(command);
    }

    private void checkDoctorExistence(Integer id) {
        if (!repository.doctorExists(id)) {
            throw new MyException(DOCTOR_NOT_FOUND);
        }
    }

}
