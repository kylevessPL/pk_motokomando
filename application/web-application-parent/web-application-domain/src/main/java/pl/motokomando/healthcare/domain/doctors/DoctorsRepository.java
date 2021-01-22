package pl.motokomando.healthcare.domain.doctors;

import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;

import java.util.Optional;

public interface DoctorsRepository {

    DoctorBasicPage getAllDoctors(PageProperties pageProperties, SortProperties sortProperties);
    Doctor getDoctorFullById(Integer id);
    Optional<Doctor> getDoctorById(Integer id);
    boolean doctorExists(Integer id);
    void saveDoctor(DoctorRequestCommand data);

}
