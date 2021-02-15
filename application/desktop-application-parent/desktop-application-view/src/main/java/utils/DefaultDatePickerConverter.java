package utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public final class DefaultDatePickerConverter extends StringConverter<LocalDate> {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return EMPTY;
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

}
