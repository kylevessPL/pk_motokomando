package pl.motokomando.healthcare.domain.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.bills.BillsRepository;
import pl.motokomando.healthcare.domain.doctors.DoctorsRepository;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentFull;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionFull;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.model.utils.BasicException;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patients.PatientsRepository;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionsRepository;
import pl.motokomando.healthcare.domain.prescriptions.medicines.PrescriptionMedicinesRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.APPOINTMENT_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.BILL_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DATE_NOT_AVAILABLE;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.DOCTOR_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentsRepository appointmentsRepository;
    private final PatientsAppointmentsRepository patientsAppointmentsRepository;
    private final PatientsRepository patientsRepository;
    private final DoctorsRepository doctorsRepository;
    private final BillsRepository billsRepository;
    private final PrescriptionsRepository prescriptionsRepository;
    private final PrescriptionMedicinesRepository prescriptionMedicinesRepository;

    @Override
    @Transactional(readOnly = true)
    public AppointmentBasicPage getAllAppointments(Integer patientId, BasicPagedQueryCommand command) {
        checkPatientExistence(patientId);
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        List<Integer> appointmentIdList = patientsAppointmentsRepository.getPatientAppointmentIdList(patientId);
        if (appointmentIdList.isEmpty()) {
            return createEmptyAppointmentBasicPage();
        }
        return appointmentsRepository.getAllAppointmentsByIdIn(appointmentIdList, pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointment(AppointmentRequestParamsCommand command) {
        Integer patientId = command.getPatientId();
        Integer appointmentId = command.getAppointmentId();
        checkPatientExistence(patientId);
        checkPatientAppointmentExistence(patientId, appointmentId);
        return appointmentsRepository.getAppointmentById(appointmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentFull getFullAppointment(AppointmentRequestParamsCommand command) {
        Integer patientId = command.getPatientId();
        Integer appointmentId = command.getAppointmentId();
        checkPatientExistence(patientId);
        checkPatientAppointmentExistence(patientId, appointmentId);
        Appointment appointment = appointmentsRepository.getAppointmentById(appointmentId);
        Bill bill = getBill(appointment.getBillId()).orElse(null);
        Doctor doctor = getDoctor(appointment.getDoctorId());
        PrescriptionFull prescription = getPrescriptionFull(appointment.getPrescriptionId()).orElse(null);
        return createAppointmentFull(appointment, bill, doctor, prescription);
    }

    @Override
    @Transactional
    public Basic createAppointment(Integer patientId, AppointmentRequestCommand command) {
        checkPatientExistence(patientId);
        checkDoctorExistence(command.getDoctorId());
        checkDateAvailability(command.getAppointmentDate());
        Basic appointmentBasic = appointmentsRepository.createAppointment(command);
        patientsAppointmentsRepository.createPatientAppointment(patientId, appointmentBasic.getId());
        return appointmentBasic;
    }

    @Override
    @Transactional
    public void updateAppointment(AppointmentPatchRequestCommand command) {
        checkDoctorExistence(command.getDoctorId());
        Optional.ofNullable(command.getBillId()).ifPresent(this::checkBillExistence);
        Optional.ofNullable(command.getPrescriptionId()).ifPresent(this::checkPrescriptionExistence);
        appointmentsRepository.updateAppointment(command);
    }

    private AppointmentFull createAppointmentFull(Appointment appointment, Bill bill, Doctor doctor, PrescriptionFull prescription) {
        return new AppointmentFull(
                appointment.getId(),
                appointment.getScheduleDate(),
                appointment.getAppointmentDate(),
                bill,
                doctor,
                prescription,
                appointment.getDiagnosis(),
                appointment.getAppointmentStatus()
        );
    }

    private AppointmentBasicPage createEmptyAppointmentBasicPage() {
        return new AppointmentBasicPage(
                new PageMeta(
                       true,
                       true,
                       false,
                       false,
                       1,
                       1,
                        0L
                ),
                Collections.emptyList()
        );
    }

    private Optional<Bill> getBill(@Nullable Integer billId) {
        return Optional.ofNullable(billId).map(billsRepository::getBillFullById);
    }

    private Doctor getDoctor(Integer doctorId) {
        return doctorsRepository.getDoctorFullById(doctorId);
    }

    private Optional<PrescriptionFull> getPrescriptionFull(@Nullable Integer prescriptionId) {
        return Optional.ofNullable(prescriptionId).map(this::createPrescriptionFull);
    }

    private PrescriptionFull createPrescriptionFull(Integer prescriptionId) {
        Prescription prescriptionDetails = prescriptionsRepository.getFullPrescriptionById(prescriptionId);
        List<PrescriptionMedicine> medicines = prescriptionMedicinesRepository.getAllPrescriptionMedicines(prescriptionId);
        return new PrescriptionFull(prescriptionDetails, medicines);
    }

    private void checkDateAvailability(LocalDateTime date) {
        if (!appointmentsRepository.isDateAvailable(date)) {
            throw new BasicException(DATE_NOT_AVAILABLE);
        }
    }

    private void checkPatientExistence(Integer patientId) {
        if (!patientsRepository.patientExists(patientId)) {
            throw new BasicException(PATIENT_NOT_FOUND);
        }
    }

    private void checkDoctorExistence(Integer doctorId) {
        if (!doctorsRepository.doctorExists(doctorId)) {
            throw new BasicException(DOCTOR_NOT_FOUND);
        }
    }

    private void checkBillExistence(Integer billId) {
        if (!billsRepository.billExists(billId)) {
            throw new BasicException(BILL_NOT_FOUND);
        }
    }

    private void checkPrescriptionExistence(Integer prescriptionId) {
        if (!prescriptionsRepository.prescriptionExists(prescriptionId)) {
            throw new BasicException(PRESCRIPTION_NOT_FOUND);
        }
    }

    private void checkPatientAppointmentExistence(Integer patientId, Integer appointmentId) {
        if (!patientsAppointmentsRepository.patientAppointmentExistsByPatientIdAndAppointmentId(patientId, appointmentId)) {
            throw new BasicException(APPOINTMENT_NOT_FOUND);
        }
    }

}
