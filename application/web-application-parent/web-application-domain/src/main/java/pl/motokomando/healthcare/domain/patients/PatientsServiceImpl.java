package pl.motokomando.healthcare.domain.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.doctors.DoctorsRepository;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.PatientHealthInfo;
import pl.motokomando.healthcare.domain.model.patients.appointments.LatestAppointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.LatestAppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.records.CurrentHealth;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicException;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsRepository;
import pl.motokomando.healthcare.domain.patients.appointments.PatientsAppointmentsRepository;
import pl.motokomando.healthcare.domain.patients.records.PatientRecordsRepository;

import java.util.List;
import java.util.Optional;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {

    private final PatientsRepository patientsRepository;
    private final PatientRecordsRepository patientRecordsRepository;
    private final AppointmentsRepository appointmentsRepository;
    private final PatientsAppointmentsRepository patientsAppointmentsRepository;
    private final DoctorsRepository doctorsRepository;

    @Override
    @Transactional(readOnly = true)
    public PatientBasicPage getAllPatients(BasicPagedQueryCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        return patientsRepository.getAllPatients(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatient(Integer id) {
        PatientDetails patientDetails = patientsRepository.getPatientById(id)
                .orElseThrow(() -> new BasicException(PATIENT_NOT_FOUND));
        PatientBasicInfo basicInfo = patientRecordsRepository.getPatientRecordBasicByPatientId(id);
        return new Patient(basicInfo, patientDetails);
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand command) {
        Optional<Integer> patientId = Optional.ofNullable(command.getId());
        patientId.ifPresent(this::checkPatientExistence);
        Integer id = patientsRepository.savePatient(command);
        if (!patientId.isPresent()) {
            patientRecordsRepository.createPatientRecord(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PatientHealthInfo getHealthInfo(Integer id) {
        CurrentHealth currentHealth = patientRecordsRepository.getCurrentHealth(id)
                .orElseThrow(() -> new BasicException(PATIENT_NOT_FOUND));
        List<Integer> appointmentIdList = patientsAppointmentsRepository.getPatientAppointmentIdList(id);
        LatestAppointment latestAppointment = getLatestAppointment(appointmentIdList).orElse(null);
        return new PatientHealthInfo(id, currentHealth, latestAppointment);
    }

    private void checkPatientExistence(Integer id) {
        if (!patientsRepository.patientExists(id)) {
            throw new BasicException(PATIENT_NOT_FOUND);
        }
    }

    private Optional<LatestAppointment> getLatestAppointment(List<Integer> appointmentIdList) {
        if (appointmentIdList.isEmpty()) {
            return Optional.empty();
        }
        LatestAppointmentBasic latestAppointmentBasic = appointmentsRepository.getLatestAppointmentBasic(appointmentIdList);
        Doctor doctor = doctorsRepository.getDoctorFullById(latestAppointmentBasic.getDoctorId());
        return Optional.of(createLatestAppointment(latestAppointmentBasic, doctor));
    }

    private LatestAppointment createLatestAppointment(LatestAppointmentBasic latestAppointmentBasic, Doctor doctor) {
        String doctorFullName = doctor.getFirstName() + ' ' + doctor.getLastName();
        return new LatestAppointment(
                latestAppointmentBasic.getAppointmentDate(),
                doctorFullName,
                latestAppointmentBasic.getDiagnosis()
        );
    }

}
