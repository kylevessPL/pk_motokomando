package pl.motokomando.healthcare.dto.bills;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class BillBasicResponse {

    @Schema(description = "Bill ID", example = "1")
    private Integer id;

}
