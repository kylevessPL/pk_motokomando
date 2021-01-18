package pl.motokomando.healthcare.domain.patients.appointments;

import java.util.List;

public interface PatientsAppointmentsRepository {

    List<Integer> getPatientAppointmentIdList(Integer id);
    void createPatientAppointment(Integer patientId, Integer appointmentId);
    boolean patientAppointmentExistsByPatientIdAndAppointmentId(Integer patientId, Integer appointmentId);

}
