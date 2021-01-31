package pl.motokomando.healthcare.domain.patients.appointments;

import pl.motokomando.healthcare.domain.model.doctors.appointments.DoctorAppointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.LatestAppointmentBasic;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentsRepository {

    AppointmentBasicPage getAllAppointmentsByIdIn(List<Integer> appointmentIdList, PageProperties pageProperties, SortProperties sortProperties);
    Appointment getAppointmentById(Integer id);
    List<DoctorAppointment> getAllAppointmentsByDoctorIdAndDateRange(Integer doctorId, LocalDate startDate, LocalDate endDate);
    LatestAppointmentBasic getLatestAppointmentBasic(List<Integer> appointmentIdList);
    void updateAppointment(AppointmentPatchRequestCommand data);
    Basic createAppointment(AppointmentRequestCommand data);
    boolean isDateAvailable(Integer doctorId, LocalDateTime date);

}
