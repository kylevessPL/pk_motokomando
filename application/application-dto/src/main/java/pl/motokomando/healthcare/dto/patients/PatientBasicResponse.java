package pl.motokomando.healthcare.dto.patients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasic;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PatientBasicResponse {

    private Integer totalPage;
    private Long totalCount;
    private List<PatientBasic> content;

}
