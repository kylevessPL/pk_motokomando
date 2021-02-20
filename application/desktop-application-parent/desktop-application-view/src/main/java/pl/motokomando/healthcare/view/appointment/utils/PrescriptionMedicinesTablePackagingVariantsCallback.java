package pl.motokomando.healthcare.view.appointment.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.model.appointment.utils.PrescriptionMedicinesTableRecord;
import utils.FXAlert;

import java.util.Arrays;
import java.util.stream.Collectors;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

@RequiredArgsConstructor
public final class PrescriptionMedicinesTablePackagingVariantsCallback implements Callback<TableColumn<PrescriptionMedicinesTableRecord, Void>, TableCell<PrescriptionMedicinesTableRecord, Void>> {

    private final TabPane pane;

    @Override
    public TableCell<PrescriptionMedicinesTableRecord, Void> call(final TableColumn<PrescriptionMedicinesTableRecord, Void> param) {
        TableCell<PrescriptionMedicinesTableRecord, Void> cell = new TableCell<PrescriptionMedicinesTableRecord, Void>() {
            private final Button button = new Button("Wyświetl");
            {
                button.setOnAction((ActionEvent event) -> {
                    String[] packagingVariants = getTableView()
                            .getItems()
                            .get(getIndex())
                            .getRecord().packagingVariants().get();
                    String contentText = Arrays
                            .stream(packagingVariants)
                            .map(e -> "\t• " + e)
                            .collect(Collectors.joining(System.lineSeparator()));
                    contentText = "Dostępne opakowania:\n" + contentText;
                    Alert alert = FXAlert.builder()
                            .alertType(INFORMATION)
                            .alertTitle("Dostępne opakowania")
                            .contentText(contentText)
                            .owner(pane.getScene().getWindow())
                            .build();
                    alert.showAndWait();
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }

        };
        cell.setAlignment(CENTER);
        return cell;
    }

}
