package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Builder;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public final class FXAlert {

    private static final String ERROR_HEADER = "Błąd";
    private static final String CONFIRMATION_HEADER = "Potwierdzenie";
    private static final String INFORMATION_HEADER = "Informacja";
    private static final String WARNING_HEADER = "Ostrzeżenie";

    @Builder
    public static Alert createAlert(AlertType alertType, String contentText, String alertTitle) {
        Alert alert = new Alert(alertType, contentText);
        alert.setTitle(alertTitle);
        alert.setHeaderText(getHeader(alertType));
        alert.getDialogPane().setPrefWidth(500);
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
