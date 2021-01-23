package pl.motokomando.healthcare.dto.miscellaneous;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DateAvailableResponse {

    @Schema(description = "Chosen appointment date")
    private LocalDateTime date;
    @Schema(description = "Date availability state")
    private boolean available;

}
