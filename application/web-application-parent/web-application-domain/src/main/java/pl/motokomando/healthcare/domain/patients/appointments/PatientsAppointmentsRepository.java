package pl.motokomando.healthcare.domain.patients.appointments;

public interface PatientsAppointmentsRepository {

    void createPatientAppointment(Integer patientId, Integer appointmentId);
    boolean patientAppointmentExistsByPatientIdAndAppointmentId(Integer patientId, Integer appointmentId);

}
