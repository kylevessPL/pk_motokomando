package utils;

import javafx.scene.control.Control;
import lombok.RequiredArgsConstructor;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import java.time.temporal.ValueRange;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static org.controlsfx.validation.Severity.ERROR;

public final class FXValidation {

    public static Validator<String> createEmptyValidator(String fieldName) {
        return Validator.createEmptyValidator(String.format("Pole '%s' nie może być puste", fieldName));
    }

    public static Validator<String> createRegexValidator(String fieldName, Pattern regex) {
        return Validator.createRegexValidator(String.format("Pole '%s' zawiera niepoprawne znaki", fieldName), regex, ERROR);
    }

    public static Validator<String> createRangeValidator(String fieldName, ValueRange range) {
        return new RangeValidator(fieldName, range);
    }

    public static Validator<Integer> createCheckComboBoxValidator(String fieldName) {
        return new CheckComboBoxValidator(fieldName);
    }

    @RequiredArgsConstructor(access = PRIVATE)
    private static class RangeValidator implements Validator<String> {

        private final String fieldName;
        private final ValueRange range;

        @Override
        public ValidationResult apply(Control control, String value) {
            String message = String.format("Pole '%s' powinno wynosić od %d do %d znaków długości",
                    fieldName, (int) range.getMinimum(), (int) range.getMaximum());
            return ValidationResult.fromErrorIf(control, message, !range.isValidValue(value.length()));
        }

    }

    @RequiredArgsConstructor(access = PRIVATE)
    private static class CheckComboBoxValidator implements Validator<Integer> {

        private final String fieldName;

        @Override
        public ValidationResult apply(Control control, Integer value) {
            String message = String.format("Dla pola '%s' wymagana jest przynajmniej jedna opcja", fieldName);
            return ValidationResult.fromErrorIf(control, message, value == 0);
        }

    }

    public static String prettyPrintErrors(Collection<?> errorList) {
        errorList = errorList
                .stream()
                .map(e -> e.toString().substring(6, e.toString().length() - 1))
                .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Popraw następujące błędy:").append(System.lineSeparator());
        errorList.forEach(error -> stringBuilder.append("\t\u2023 ").append(error).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

}
