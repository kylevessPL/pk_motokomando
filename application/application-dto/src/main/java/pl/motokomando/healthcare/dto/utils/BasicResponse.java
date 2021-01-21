package pl.motokomando.healthcare.dto.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class BasicResponse {

    @Schema(description = "Resource ID", example = "1")
    private Integer id;

}
