package utils;

import javafx.util.StringConverter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@NoArgsConstructor(access = PRIVATE)
public final class DefaultDatePickerConverter extends StringConverter<LocalDate> {

    private static final StringConverter<LocalDate> INSTANCE = new DefaultDatePickerConverter();

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

    public static StringConverter<LocalDate> getInstance() {
        return INSTANCE;
    }

}
