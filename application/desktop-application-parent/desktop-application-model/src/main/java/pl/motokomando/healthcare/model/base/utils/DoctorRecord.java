package pl.motokomando.healthcare.model.base.utils;

import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class DoctorRecord {
    SimpleStringProperty tableColumnAcademicTittle;
    String tableColumnDoctorName;
    String tableColumnDoctorSurname;
    String tableColumnDoctorPhoneNumber;
    String tableColumnDoctorSpecialisation;
}
