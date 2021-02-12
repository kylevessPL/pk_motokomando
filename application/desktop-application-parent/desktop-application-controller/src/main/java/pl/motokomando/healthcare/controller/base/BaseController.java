package pl.motokomando.healthcare.controller.base;

import javafx.scene.control.TableRow;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;

public class BaseController {

    private final BaseModel baseModel;

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public TableRow<?> nazwa() {
        TableRow<DoctorRecord> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                DoctorRecord rowData = row.getItem();
                System.out.println("działa");
            }
        });
        return row;
    }

}
