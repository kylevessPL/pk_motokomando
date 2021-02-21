package pl.motokomando.healthcare.model.doctor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.DoctorDetails;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
@RequiredArgsConstructor
public final class DoctorModel {

    private final Integer doctorId;

    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleObjectProperty<DoctorDetails> doctorDetails = new SimpleObjectProperty<>();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty doctorSpecialtyComboBoxCheckedItemsNumber = new SimpleIntegerProperty();

    public void setDoctorSpecialtyComboBoxCheckedItemsNumber(Integer value) {
        doctorSpecialtyComboBoxCheckedItemsNumber.set(value);
    }

    public DoctorDetails getDoctorDetails() {
        return doctorDetails.get();
    }

    public void setDoctorDetails(DoctorDetails doctorDetails) {
        this.doctorDetails.set(doctorDetails);
    }

}
