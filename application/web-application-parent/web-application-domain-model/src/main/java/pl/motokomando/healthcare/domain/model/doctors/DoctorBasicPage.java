package pl.motokomando.healthcare.domain.model.doctors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class DoctorBasicPage {

    private final PageMeta meta;
    private final List<DoctorBasic> content;

}
