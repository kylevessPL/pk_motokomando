package pl.motokomando.healthcare.model.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.PersonTableRecord;

import java.util.List;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
public final class BaseModel {

    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty doctorSpecialtyComboBoxCheckedItemsNumber = new SimpleIntegerProperty();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty patientsTableTotalPages = new SimpleIntegerProperty(1);
    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty doctorsTableTotalPages = new SimpleIntegerProperty(1);
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<PersonTableRecord> patientsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<PersonTableRecord> doctorsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private Integer patientsTableCurrentPage = 1;
    private Integer doctorsTableCurrentPage = 1;

    @Setter(NONE)
    private Integer tableCountPerPage = 19;

    public void setDoctorSpecialtyComboBoxCheckedItemsNumber(Integer value) {
        doctorSpecialtyComboBoxCheckedItemsNumber.set(value);
    }

    public void setPatientsTableTotalPages(Integer value) {
        patientsTableTotalPages.set(value);
    }

    public void setDoctorsTableTotalPages(Integer value) {
        doctorsTableTotalPages.set(value);
    }

    public void setPatientsTablePageContent(List<PersonTableRecord> tablePageContent) {
        patientsTablePageContent.setAll(tablePageContent);
    }

    public void setDoctorsTablePageContent(List<PersonTableRecord> tablePageContent) {
        doctorsTablePageContent.setAll(tablePageContent);
    }

}
