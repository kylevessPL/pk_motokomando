package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionsEntity;

import java.util.Optional;

@Component
public class PrescriptionsEntityMapper {

    public Optional<Prescription> mapToPrescription(Optional<PrescriptionsEntity> prescriptionsEntity) {
        return prescriptionsEntity.map(this::createPrescription);
    }

    public PrescriptionBasic mapToPrescriptionBasic(Integer id) {
        return createPrescriptionBasic(id);
    }

    private Prescription createPrescription(PrescriptionsEntity prescriptionsEntity) {
        return new Prescription(
                prescriptionsEntity.getId(),
                prescriptionsEntity.getIssueDate().toLocalDateTime(),
                prescriptionsEntity.getExpirationDate(),
                prescriptionsEntity.getNotes());
    }

    private PrescriptionBasic createPrescriptionBasic(Integer id) {
        return new PrescriptionBasic(id);
    }

}
