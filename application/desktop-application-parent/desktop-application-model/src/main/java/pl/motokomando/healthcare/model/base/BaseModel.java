package pl.motokomando.healthcare.model.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.BaseTableRecord;

import java.util.List;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
public class BaseModel {

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
    private final ObservableList<BaseTableRecord> patientsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<BaseTableRecord> doctorsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private Integer patientsTableCurrentPage = 1;
    private Integer doctorsTableCurrentPage = 1;
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

    public void setPatientsTablePageContent(List<BaseTableRecord> tablePageContent) {
        patientsTablePageContent.setAll(tablePageContent);
    }

    public void setDoctorsTablePageContent(List<BaseTableRecord> tablePageContent) {
        doctorsTablePageContent.setAll(tablePageContent);
    }

}
