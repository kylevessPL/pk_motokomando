package pl.motokomando.healthcare.domain.model.patients.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.utils.SortDirection;

@NoArgsConstructor
@Getter
@Setter
public class PatientQueryCommand {

    private Integer page;
    private Integer size;
    private String sortBy;
    private SortDirection sortDir;

}
