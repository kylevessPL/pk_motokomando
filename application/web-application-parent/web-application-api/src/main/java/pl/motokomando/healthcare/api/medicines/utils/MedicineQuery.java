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
    @NotBlank(message = "Query string not supplied")
    private String query;
    @ApiParam(value = "Results limit", defaultValue = "1", example = "5")
    @Min(value = 1, message = "Limit must be a positive integer value")
    private Integer limit;

}
