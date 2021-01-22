package pl.motokomando.healthcare.domain.model.patients.records;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.records.utils.HealthStatus;

@RequiredArgsConstructor
@Getter
public final class CurrentHealth {

    private final HealthStatus healthStatus;
    private final String notes;

}
