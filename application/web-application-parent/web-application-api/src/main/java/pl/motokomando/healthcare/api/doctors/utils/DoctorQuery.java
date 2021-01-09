package pl.motokomando.healthcare.api.doctors.utils;

import io.swagger.annotations.ApiParam;
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
public class DoctorQuery {

    @ApiParam(value = "Page number", defaultValue = "1", example = "5")
    @Min(value = 1, message = "Page number must be a positive integer value")
    private Integer page = 1;
    @ApiParam(value = "Page size", defaultValue = "10", example = "50")
    @Min(value = 1, message = "Page size must be a positive integer value")
    private Integer size = 20;
    @ApiParam(value = "Value to sort room by", defaultValue = "id", allowableValues = "id, firstName, lastName, specialty", example = "specialty")
    @ValidateString(acceptedValues = {"id", "firstName", "lastName", "specialty"}, message = "Sort by property not valid")
    private String sortBy = "id";
    @ApiParam(value = "Sort direction", defaultValue = "ASC", allowableValues = "ASC, DESC")
    private SortDirection sortDir = ASC;

}
