package pl.motokomando.healthcare.view.patient.utils;

import javafx.util.StringConverter;
import pl.motokomando.healthcare.model.patient.utils.DoctorBasic;

public final class ChooseDoctorComboBoxConverter extends StringConverter<DoctorBasic> {

    @Override
    public String toString(DoctorBasic object) {
        return String.format("%s %s", object.getFirstName(), object.getLastName());
    }

    @Override
    public DoctorBasic fromString(String string) {
        return null;
    }

}
