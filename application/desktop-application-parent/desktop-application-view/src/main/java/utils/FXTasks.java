package utils;

import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.util.concurrent.Callable;

public class FXTasks {

    public static <T> Task<T> create(Callable<T> callable) {
        return new Task<T>() {
            @Override
            public T call() throws Exception {
                return callable.call();
            }
        };
    }

    public static void clearControl(Control control) {
        if (control instanceof TextField) {
            ((TextField) control).clear();
        } else if (control instanceof ComboBox) {
            ((ComboBox<?>) control).getSelectionModel().clearSelection();
        } else {
            ((CheckComboBox<?>) control).getCheckModel().clearChecks();
        }
    }

}
