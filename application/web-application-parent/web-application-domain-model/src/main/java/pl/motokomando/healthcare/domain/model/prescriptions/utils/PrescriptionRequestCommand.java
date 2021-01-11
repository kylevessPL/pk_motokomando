package pl.motokomando.healthcare.domain.model.prescriptions.utils;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PrescriptionRequestCommand {

    private LocalDate expirationDate;
    @Nullable
    private String notes;

}
