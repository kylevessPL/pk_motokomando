package pl.motokomando.healthcare.view.appointment.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.model.appointment.utils.ActiveIngredient;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import pl.motokomando.healthcare.view.utils.FXAlert;

import java.util.List;
import java.util.stream.Collectors;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

@RequiredArgsConstructor
public final class MedicinesTableActiveIngredientsCallback implements Callback<TableColumn<MedicinesTableRecord, Void>, TableCell<MedicinesTableRecord, Void>> {

    private final TabPane pane;

    @Override
    public TableCell<MedicinesTableRecord, Void> call(final TableColumn<MedicinesTableRecord, Void> param) {
        TableCell<MedicinesTableRecord, Void> cell = new TableCell<MedicinesTableRecord, Void>() {
            private final Button button = new Button("Wyświetl");
            {
                button.setOnAction((ActionEvent event) -> {
                    List<ActiveIngredient> activeIngredientList = getTableView()
                            .getItems()
                            .get(getIndex())
                            .activeIngredients();
                    String contentText = activeIngredientList
                            .stream()
                            .map(e -> "\t• " + e.toString())
                            .collect(Collectors.joining(System.lineSeparator()));
                    contentText = "Skład leku:\n" + contentText;
                    Alert alert = FXAlert.builder()
                            .alertType(INFORMATION)
                            .alertTitle("Skład leku")
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
