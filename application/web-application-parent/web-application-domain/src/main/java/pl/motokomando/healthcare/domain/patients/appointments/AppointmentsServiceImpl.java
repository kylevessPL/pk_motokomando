package pl.motokomando.healthcare.domain.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.doctors.DoctorsRepository;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.patients.PatientsRepository;

import java.time.LocalDateTime;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DATE_NOT_AVAILABLE;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DOCTOR_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentsRepository appointmentsRepository;
    private final PatientsAppointmentsRepository patientsAppointmentsRepository;
    private final PatientsRepository patientsRepository;
    private final DoctorsRepository doctorsRepository;

    @Override
    @Transactional
    public AppointmentBasic createAppointment(Integer patientId, AppointmentRequestCommand request) {
        checkPatientExistence(patientId);
        checkDoctorExistence(request.getDoctorId());
        checkDateAvailability(request.getAppointmentDate());
        AppointmentBasic appointmentBasic = appointmentsRepository.createAppointment(request);
        patientsAppointmentsRepository.createPatientAppointment(patientId, appointmentBasic.getId());
        return appointmentBasic;
    }

    private void checkDateAvailability(LocalDateTime date) {
        if (!appointmentsRepository.isDateAvailable(date)) {
            throw new MyException(DATE_NOT_AVAILABLE);
        }
    }

    private void checkPatientExistence(Integer id) {
        if (!patientsRepository.patientExists(id)) {
            throw new MyException(PATIENT_NOT_FOUND);
        }
    }

    private void checkDoctorExistence(Integer id) {
        if (!doctorsRepository.doctorExists(id)) {
            throw new MyException(DOCTOR_NOT_FOUND);
        }
    }

}
