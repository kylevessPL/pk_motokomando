package pl.motokomando.healthcare.dto.medicines.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class ActiveIngredient {

    @Schema(description = "Ingredient name", example = "FOSINOPRIL SODIUM")
    private String name;
    @Schema(description = "Ingredient strength", example = "20 mg/1")
    private String strength;

}
