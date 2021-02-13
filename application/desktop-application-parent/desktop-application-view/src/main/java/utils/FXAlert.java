package utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import lombok.Builder;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public final class FXAlert {

    private static final String ERROR_HEADER = "Błąd";
    private static final String CONFIRMATION_HEADER = "Potwierdzenie";
    private static final String INFORMATION_HEADER = "Informacja";
    private static final String WARNING_HEADER = "Ostrzeżenie";

    @Builder
    public static Alert createAlert(AlertType alertType, String contentText, String alertTitle) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Alert alert = new Alert(alertType, contentText);
        alert.setTitle(alertTitle);
        alert.setHeaderText(getHeader(alertType));
        alert.getDialogPane().setPrefWidth(500);
        alert.setX((screenBounds.getWidth() - alert.getDialogPane().getPrefWidth()) / 2);
        alert.setY((screenBounds.getHeight() - alert.getHeight()) / 2);
        return alert;
    }

    private static String getHeader(AlertType alertType) {
        switch (alertType) {
            case ERROR:
                return ERROR_HEADER;
            case CONFIRMATION:
                return CONFIRMATION_HEADER;
            case INFORMATION:
                return INFORMATION_HEADER;
            case WARNING:
                return WARNING_HEADER;
            default:
                return EMPTY;
        }
    }

}
