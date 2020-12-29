package pl.motokomando.healthcare.domain.model.medicines.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MedicineCommand {

    private String query;
    private Integer limit;

}
