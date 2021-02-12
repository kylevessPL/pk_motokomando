package pl.motokomando.healthcare.controller.base;

import javafx.scene.control.TableRow;
import pl.motokomando.healthcare.model.base.BaseModel;

public class BaseController {

    private final BaseModel baseModel;

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    //TODO nie tu
    public TableRow<?> nazwa() {
        TableRow<DoctorRecord> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                DoctorRecord rowData = row.getItem();
                System.out.println("działa");
            }
        });
        return row;
    }*/

    public void handleAddDoctorButtonClicked() {

    }

}
