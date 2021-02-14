package pl.motokomando.healthcare.model.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;

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
    private final IntegerProperty doctorsTableTotalPages = new SimpleIntegerProperty(1);
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<DoctorRecord> doctorsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private Integer doctorsTableCurrentPage = 1;
    private Integer tableCountPerPage = 19;

    public void setDoctorSpecialtyComboBoxCheckedItemsNumber(Integer value) {
        doctorSpecialtyComboBoxCheckedItemsNumber.set(value);
    }

    public void setDoctorsTableTotalPages(Integer value) {
        doctorsTableTotalPages.set(value);
    }

    public void setDoctorsTablePageContent(List<DoctorRecord> tablePageContent) {
        doctorsTablePageContent.setAll(tablePageContent);
    }

}
