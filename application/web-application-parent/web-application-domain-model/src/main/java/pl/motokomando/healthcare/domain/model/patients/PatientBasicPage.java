package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PatientBasicPage {

    private final Integer totalPage;
    private final Long totalCount;
    private final List<PatientBasic> content;

}
