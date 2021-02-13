package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Builder;

public final class FXAlert {

    @Builder
    public static Alert getAlert(AlertType alertType, String contentText, String alertTitle) {
        Alert alert = new Alert(alertType, contentText);
        alert.setTitle(alertTitle);
        return alert;
    }

}
