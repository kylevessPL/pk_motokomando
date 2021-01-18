package pl.motokomando.healthcare.dto.doctors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.doctors.utils.DoctorBasicPaged;
import pl.motokomando.healthcare.dto.utils.PageMetaResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DoctorBasicPageResponse {

    private PageMetaResponse meta;
    private List<DoctorBasicPaged> content;

}
