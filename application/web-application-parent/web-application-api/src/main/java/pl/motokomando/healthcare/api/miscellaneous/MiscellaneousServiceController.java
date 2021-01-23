package pl.motokomando.healthcare.api.miscellaneous;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.motokomando.healthcare.api.miscellaneous.mapper.MiscellaneousMapper;
import pl.motokomando.healthcare.api.utils.ValidateDate;
import pl.motokomando.healthcare.domain.miscellaneous.MiscellaneousService;
import pl.motokomando.healthcare.dto.miscellaneous.DateAvailableResponse;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/miscellaneous")
@Tag(name = "Miscellaneous API", description = "API performing miscellaneous operations")
@Validated
@RequiredArgsConstructor
public class MiscellaneousServiceController {

    private final MiscellaneousService miscellaneousService;
    private final MiscellaneousMapper miscellaneousMapper;

    @Operation(
            summary = "Check appointment date availability",
            description = "You are required to pass ISO datetime",
            operationId = "checkDateAvailability"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked date avilability"),
            @ApiResponse(responseCode = "400", description = "Parameters not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/availabledate", produces = APPLICATION_JSON_VALUE)
    public DateAvailableResponse checkDateAvailability(
            @Parameter(description = "Appointment date")
            @Future(message = "Date must be in the future")
            @DateTimeFormat(iso = DATE_TIME)
            @ValidateDate(dayFrom = 1, dayTo = 5, hourFrom = 8, hourTo = 18, appointmentDuration = 30)
            @RequestParam LocalDateTime date) {
        return miscellaneousMapper.mapToResponse(miscellaneousService.checkDateAvailability(date));
    }

}
