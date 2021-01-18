package pl.motokomando.healthcare.domain.doctors;

import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;

public interface DoctorsService {

    DoctorBasicPage getAllDoctors(BasicPagedQueryCommand command);
    Doctor getDoctor(Integer id);
    void saveDoctor(DoctorRequestCommand command);

}
