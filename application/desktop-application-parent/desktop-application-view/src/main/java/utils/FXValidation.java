package utils;

import org.controlsfx.validation.Validator;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.controlsfx.validation.Severity.ERROR;
import static utils.DateConstraints.FUTURE;

public final class FXValidation {

    public static <T> Validator<T> createEmptyValidator(String fieldName) {
        String message = String.format("Pole '%s' nie może być puste", fieldName);
        return Validator.createEmptyValidator(message);
    }

    public static Validator<String> createRegexValidator(String fieldName, Pattern regex) {
        String message = String.format("Pole '%s' zawiera niepoprawne znaki", fieldName);
        return Validator.createRegexValidator(message, regex, ERROR);
    }

    public static Validator<String> createMinLengthValidator(String fieldName, Integer minLength) {
        String message = String.format("Pole '%s' powinno liczyć minimum %s znaków długości", fieldName, minLength);
        return Validator.createPredicateValidator(value -> value.length() >= minLength, message);
    }

    public static Validator<Integer> createCheckComboBoxValidator(String fieldName) {
        String message = String.format("Dla pola '%s' wymagana jest przynajmniej jedna opcja", fieldName);
        return Validator.createPredicateValidator(value -> value != 0, message);
    }

    public static Validator<LocalDate> createDateValidator(String fieldName, DateConstraints dateConstraint) {
        String message = String.format("Pole '%s' musi zawierać datę w %s",
                fieldName, (dateConstraint.equals(FUTURE) ? "przyszłości" : "przeszłości"));
        return Validator.createPredicateValidator(isDateValid(dateConstraint), message);
    }

    private static Predicate<LocalDate> isDateValid(DateConstraints dateConstraint) {
        return value -> {
            if (dateConstraint.equals(FUTURE)) {
                return value == null || value.isAfter(LocalDate.now());
            }
            return value == null || value.isBefore(LocalDate.now());
        };
    }

}
