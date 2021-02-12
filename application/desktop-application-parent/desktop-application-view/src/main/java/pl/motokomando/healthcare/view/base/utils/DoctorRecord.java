package pl.motokomando.healthcare.view.base.utils;

import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class DoctorRecord {

    SimpleStringProperty tableColumnAcademicTitle;
    String tableColumnDoctorName;
    String tableColumnDoctorSurname;
    String tableColumnDoctorPhoneNumber;
    String tableColumnDoctorSpecialisation;

}
