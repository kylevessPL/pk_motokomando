package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionsEntity;

import java.util.Optional;

@Component
public class PrescriptionsEntityMapper {

    public Optional<Prescription> mapToPrescription(Optional<PrescriptionsEntity> prescriptionsEntity) {
        return prescriptionsEntity.map(this::createPrescription);
    }

    private Prescription createPrescription(PrescriptionsEntity prescriptionsEntity) {
        return new Prescription(
                prescriptionsEntity.getId(),
                prescriptionsEntity.getIssueDate().toLocalDateTime(),
                prescriptionsEntity.getExpirationDate(),
                prescriptionsEntity.getNotes());
    }

}
