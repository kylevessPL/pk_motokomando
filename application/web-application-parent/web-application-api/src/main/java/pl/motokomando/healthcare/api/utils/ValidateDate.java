package pl.motokomando.healthcare.api.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
public @interface ValidateDate {

    int dayFrom();
    int dayTo();
    int hourFrom();
    int hourTo();
    int appointmentDuration();

    String message() default "Date must be within working hours";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
