package pl.motokomando.healthcare.domain.model.prescriptions.utils;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PrescriptionPatchRequestCommand {

    private Integer id;
    private LocalDateTime issueDate;
    private LocalDate expirationDate;
    @Nullable
    private String notes;

}
