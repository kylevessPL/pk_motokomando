package pl.motokomando.healthcare.api.medicines.utils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Parameter(
            description = "Results limit",
            schema = @Schema(type = "integer", defaultValue = "1"))
    @Min(value = 1, message = "Limit must be a positive integer value")
    private Integer limit;

}
