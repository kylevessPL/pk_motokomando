package pl.motokomando.healthcare.domain.doctors;

import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;

public interface DoctorsService {

    DoctorBasicPage getAllDoctors(BasicQueryCommand query);
    Doctor getDoctorById(Integer id);
    void saveDoctor(DoctorRequestCommand request);

}