package pl.motokomando.healthcare.domain.model.prescriptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class Prescription {

    private final Integer id;
    private final LocalDateTime issueDate;
    private final LocalDate expirationDate;
    private final String notes;

}
