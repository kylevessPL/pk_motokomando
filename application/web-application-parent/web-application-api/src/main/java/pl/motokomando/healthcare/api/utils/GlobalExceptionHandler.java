package pl.motokomando.healthcare.api.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;

import javax.json.JsonException;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.VALIDATION_FAILED;

@ControllerAdvice
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoMedicinesFoundException.class)
    protected ResponseEntity<Object> handleNoMedicinesFound(NoMedicinesFoundException ex) {
        HttpStatus status = NOT_FOUND;
        logger.info(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ex.getErrorCode().getCode(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = MyException.class)
    protected ResponseEntity<Object> handleCustomError(MyException ex) {
        HttpStatus status = BAD_REQUEST;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ex.getErrorCode().getCode(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            JsonException.class
    })
    protected ResponseEntity<Object> handleValidationError(Exception ex) {
        HttpStatus status = BAD_REQUEST;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                VALIDATION_FAILED.getCode(),
                VALIDATION_FAILED.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> anyExceptionHandler(Exception ex, WebRequest request) {
        try {
            return super.handleException(ex, request);
        } catch (Exception e) {
            return handleExceptionInternal(ex, null, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleValidationError(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleValidationError(ex);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(@NonNull BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return handleValidationError(ex);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, @Nullable Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                "",
                status.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, headers, status);
    }

}
