package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PatientBasicPage {

    private final PageMeta meta;
    private final List<PatientBasic> content;

}
