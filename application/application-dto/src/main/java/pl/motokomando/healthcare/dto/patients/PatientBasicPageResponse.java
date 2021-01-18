package pl.motokomando.healthcare.dto.patients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasicPaged;
import pl.motokomando.healthcare.dto.utils.PageMetaResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PatientBasicPageResponse {

    private PageMetaResponse meta;
    private List<PatientBasicPaged> content;

}
