package pl.motokomando.healthcare.dto.medicines.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class ActiveIngredient {

    @ApiModelProperty(value = "Ingredient name", example = "FOSINOPRIL SODIUM")
    private String name;
    @ApiModelProperty(value = "Ingredient strength", example = "20 mg/1")
    private String strength;

}
