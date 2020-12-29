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
public class Packaging {

    @ApiModelProperty(value = "Packaging variant description", example = "1000 TABLET in 1 BOTTLE (76282-201-10)")
    private String description;

}
