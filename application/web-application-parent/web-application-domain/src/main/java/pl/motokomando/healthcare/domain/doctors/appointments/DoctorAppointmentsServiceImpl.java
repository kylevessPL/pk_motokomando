package pl.motokomando.healthcare.domain.doctors.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.doctors.DoctorsRepository;
import pl.motokomando.healthcare.domain.model.doctors.appointments.DoctorAppointment;
import pl.motokomando.healthcare.domain.model.doctors.appointments.utils.DoctorAppointmentQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicException;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsRepository;

import java.util.List;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DOCTOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DoctorAppointmentsServiceImpl implements DoctorAppointmentsService {

    private final DoctorsRepository doctorsRepository;
    private final AppointmentsRepository appointmentsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorAppointment> getDoctorAppointments(Integer id, DoctorAppointmentQueryCommand command) {
        checkDoctorExistence(id);
        return appointmentsRepository.getAllAppointmentsByDoctorIdAndDateRange(
                id,
                command.getStartDate(),
                command.getEndDate());
    }

    private void checkDoctorExistence(Integer id) {
        if (!doctorsRepository.doctorExists(id)) {
            throw new BasicException(DOCTOR_NOT_FOUND);
        }
    }

}
