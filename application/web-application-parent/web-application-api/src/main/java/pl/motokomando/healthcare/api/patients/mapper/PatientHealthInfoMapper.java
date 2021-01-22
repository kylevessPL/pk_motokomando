package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.patients.appointments.LatestAppointment;
import pl.motokomando.healthcare.domain.model.patients.records.CurrentHealth;
import pl.motokomando.healthcare.dto.patients.appointments.LatestAppointmentResponse;
import pl.motokomando.healthcare.dto.patients.records.CurrentHealthResponse;

@Mapper
public interface PatientHealthInfoMapper {

    CurrentHealthResponse map(CurrentHealth currentHealth);
    LatestAppointmentResponse map(LatestAppointment latestAppointment);

}
