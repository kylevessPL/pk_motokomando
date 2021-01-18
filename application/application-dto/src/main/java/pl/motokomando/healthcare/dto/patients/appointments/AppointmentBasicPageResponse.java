package pl.motokomando.healthcare.dto.patients.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentBasicPaged;
import pl.motokomando.healthcare.dto.utils.PageMetaResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentBasicPageResponse {

    private PageMetaResponse meta;
    private List<AppointmentBasicPaged> content;

}
