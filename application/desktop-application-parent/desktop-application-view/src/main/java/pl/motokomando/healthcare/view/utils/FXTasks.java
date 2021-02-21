package pl.motokomando.healthcare.view.utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public final class FXTasks {

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
        } else if (control instanceof CheckComboBox){
            ((CheckComboBox<?>) control).getCheckModel().clearChecks();
        } else {
            ((DatePicker) control).setValue(null);
        }
    }

    public static <T> Map<String, String> getObjectsDiff(@NonNull T object1, @NonNull T object2) throws IllegalAccessException {
        Field[] fields = object1.getClass().getDeclaredFields();
        Map<String, String> diffMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Objects.equals(field.get(object1), field.get(object2))) {
                String name = field.getName();
                String value = getObjectAsAstring(field.get(object2));
                diffMap.put(name, value);
            }
        }
        return diffMap;
    }

    private static <T> Task<T> newTask(Callable<T> callable) {
        return new Task<T>() {

            @Override
            public T call() throws Exception {
                return callable.call();
            }

        };
    }

    private static String getObjectAsAstring(Object object) {
        if (object instanceof LocalDate) {
            return ((LocalDate) object).format(ISO_DATE);
        } else if (object instanceof LocalDateTime) {
            return ((LocalDateTime) object).format(ISO_DATE_TIME);
        }
        return object.toString();
    }

}
