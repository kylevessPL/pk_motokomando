package pl.motokomando.healthcare.dto.bills;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class BillBasicResponse {

    @ApiModelProperty(value = "Bill ID", example = "1")
    private Integer id;

}
