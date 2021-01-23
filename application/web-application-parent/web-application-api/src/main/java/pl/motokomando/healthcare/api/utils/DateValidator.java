package pl.motokomando.healthcare.api.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateValidator implements ConstraintValidator<ValidateDate, LocalDateTime> {

    private int dayFrom;
    private int dayTo;
    private int hourFrom;
    private int hourTo;
    private int appointmentDuration;

    @Override
    public void initialize(final ValidateDate constraintAnnotation) {
        dayFrom = constraintAnnotation.dayFrom();
        dayTo = constraintAnnotation.dayTo();
        hourFrom = constraintAnnotation.hourFrom();
        hourTo = constraintAnnotation.hourTo();
        appointmentDuration = constraintAnnotation.appointmentDuration();
    }

    @Override
    public boolean isValid(final LocalDateTime value, final ConstraintValidatorContext context) {
        List<Integer> allowedMinutes = IntStream.rangeClosed(0, 59)
                .filter(n -> n % appointmentDuration == 0 || n % appointmentDuration == appointmentDuration)
                .boxed()
                .collect(Collectors.toList());
        return value != null &&
                value.getHour() >= hourFrom && value.getHour() <= hourTo &&
                value.getDayOfWeek().getValue() >= dayFrom && value.getDayOfWeek().getValue() <= dayTo &&
                allowedMinutes.contains(value.getMinute());
    }

}
