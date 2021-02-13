package pl.motokomando.healthcare.controller.base;

import javafx.collections.ObservableList;
import pl.motokomando.healthcare.model.base.BaseModel;

public class BaseController {

    private final BaseModel baseModel;

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    //TODO nie tu
    /*public TableRow<?> nazwa() {
        TableRow<DoctorRecord> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                DoctorRecord rowData = row.getItem();
                System.out.println("działa");
            }
        });
        return row;
    }*/

    public void handleDoctorSpecialtyComboBoxCheckedItemsChanged(ObservableList<?> itemList) {
        Integer checkedItemsNumber = itemList.size();
        baseModel.setDoctorSpecialtyComboBoxCheckedItemsNumber(checkedItemsNumber);
    }

    public void handleAddDoctorButtonClicked() {

    }

}
