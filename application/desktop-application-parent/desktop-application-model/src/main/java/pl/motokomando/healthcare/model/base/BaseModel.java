package pl.motokomando.healthcare.model.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
public class BaseModel {

    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty doctorSpecialtyComboBoxCheckedItemsNumber = new SimpleIntegerProperty();

    public void setDoctorSpecialtyComboBoxCheckedItemsNumber(Integer value) {
        doctorSpecialtyComboBoxCheckedItemsNumber.set(value);
    }

}
