package pl.motokomando.healthcare.domain.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import java.time.LocalDateTime;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DATE_NOT_AVAILABLE;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentsRepository appointmentsRepository;
    private final PatientsAppointmentsRepository patientsAppointmentsRepository;

    @Override
    @Transactional
    public AppointmentBasic createAppointment(Integer patientId, AppointmentRequestCommand request) {
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

}
