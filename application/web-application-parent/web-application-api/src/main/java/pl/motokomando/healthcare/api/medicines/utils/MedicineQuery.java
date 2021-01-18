package pl.motokomando.healthcare.api.medicines.utils;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class MedicineQuery {

    @Parameter(description = "Query string", example = "Vicks VapoRub")
    @NotBlank(message = "Query string not supplied")
    private String query;
    @Parameter(description = "Results limit, default: 1", example = "5")
    @Min(value = 1, message = "Limit must be a positive integer value")
    private Integer limit;

}
