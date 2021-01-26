package pl.motokomando.healthcare.api.doctors.utils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.api.utils.ValidateString;
import pl.motokomando.healthcare.domain.model.utils.SortDirection;

import javax.validation.constraints.Min;

import static pl.motokomando.healthcare.domain.model.utils.SortDirection.ASC;

@NoArgsConstructor
@Getter
@Setter
public class DoctorPagedQuery {

    @Parameter(
            description = "Page number",
            schema = @Schema(type = "integer", defaultValue = "1"))
    @Min(value = 1, message = "Page number must be a positive integer value")
    private Integer page = 1;
    @Parameter(
            description = "Page size",
            schema = @Schema(type = "integer", defaultValue = "20"))
    @Min(value = 1, message = "Page size must be a positive integer value")
    private Integer size = 20;
    @Parameter(
            description = "Value to sort by",
            schema = @Schema(type = "string", allowableValues = { "id", "firstName", "lastName", "specialty" }, defaultValue = "id"))
    @ValidateString(acceptedValues = {"id", "firstName", "lastName", "specialty"}, message = "Sort by property not valid")
    private String sortBy = "id";
    @Parameter(description = "Sort direction", example = "ASC")
    private SortDirection sortDir = ASC;

}
