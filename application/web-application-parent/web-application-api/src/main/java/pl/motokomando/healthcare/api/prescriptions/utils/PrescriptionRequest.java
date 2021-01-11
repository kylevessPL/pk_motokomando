package pl.motokomando.healthcare.api.prescriptions.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionRequest {

    @ApiModelProperty(value = "Prescription expiration date")
    @NotNull(message = "Expiration date is mandatory")
    @Future(message = "Expiration date must be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
    @ApiModelProperty(value = "Prescription additional notes")
    @Size(min = 5, max = 100, message = "Notes must be between 5 and 100 characters long")
    @Nullable
    private String notes;

}
