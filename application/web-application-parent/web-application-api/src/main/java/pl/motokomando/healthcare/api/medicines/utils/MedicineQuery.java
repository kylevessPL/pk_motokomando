package pl.motokomando.healthcare.api.medicines.utils;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;



@NoArgsConstructor
@Getter
@Setter
public class MedicineQuery {

    @ApiParam(value = "Query string", example = "Vicks VapoRub")
    @NotBlank
    private String query;

    @ApiParam(value = "Results limit", example = "5")
    @Min(1)
    private Integer limit;

}
