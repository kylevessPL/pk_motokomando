package pl.motokomando.healthcare.domain.model.patients.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentBasicPaged;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class AppointmentBasicPage {

    private final PageMeta meta;
    private final List<AppointmentBasicPaged> content;

}
