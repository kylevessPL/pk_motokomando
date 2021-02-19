package pl.motokomando.healthcare.view.appointment.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import utils.FXAlert;

import java.util.Arrays;
import java.util.stream.Collectors;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

@RequiredArgsConstructor
public final class AdministrationRouteColumnCallback implements Callback<TableColumn<MedicinesTableRecord, Void>, TableCell<MedicinesTableRecord, Void>> {

    private final TabPane pane;

    @Override
    public TableCell<MedicinesTableRecord, Void> call(final TableColumn<MedicinesTableRecord, Void> param) {
        TableCell<MedicinesTableRecord, Void> cell = new TableCell<MedicinesTableRecord, Void>() {
            private final Button button = new Button("Wyświetl");
            {
                button.setOnAction((ActionEvent event) -> {
                    String[] packagingVariants = getTableView()
                            .getItems()
                            .get(getIndex())
                            .administrationRoute().get();
                    String contentText = Arrays
                            .stream(packagingVariants)
                            .map(e -> "\t• " + e)
                            .collect(Collectors.joining(System.lineSeparator()));
                    contentText = "Możliwe sposoby podania leku:\n" + contentText;
                    Alert alert = FXAlert.builder()
                            .alertType(INFORMATION)
                            .alertTitle("Sposób podania leku")
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
