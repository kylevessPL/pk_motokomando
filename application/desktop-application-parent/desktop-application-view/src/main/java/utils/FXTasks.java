package utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.util.concurrent.Callable;

public class FXTasks {

    public static <T> Task<T> createTask(Callable<T> callable) {
        return newTask(callable);
    }

    public static <T> ScheduledService<T> createService(Callable<T> callable) {
        return new ScheduledService<T>() {
            @Override
            protected Task<T> createTask() {
                return newTask(callable);
            }
        };
    }

    public static void clearControlState(Control control) {
        if (control instanceof TextField) {
            ((TextField) control).clear();
        } else if (control instanceof ComboBox) {
            ((ComboBox<?>) control).getSelectionModel().clearSelection();
        } else {
            ((CheckComboBox<?>) control).getCheckModel().clearChecks();
        }
    }

    private static <T> Task<T> newTask(Callable<T> callable) {
        return new Task<T>() {
            @Override
            public T call() throws Exception {
                return callable.call();
            }
        };
    }

}
