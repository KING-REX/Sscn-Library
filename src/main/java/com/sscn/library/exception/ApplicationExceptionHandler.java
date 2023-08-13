package com.sscn.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundExceptions(NotFoundException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateValueException.class)
    public Map<String, String> handleDuplicateValueExceptions(DuplicateValueException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleIllegalStateExceptions(IllegalStateException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidArgumentException.class)
    public Map<String, String> handleInvalidArgumentExceptions(InvalidArgumentException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentExceptions(IllegalArgumentException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleSQLIntegrityConstraintViolationExceptions(SQLIntegrityConstraintViolationException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeExceptions(SQLIntegrityConstraintViolationException exception){
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", exception.getMessage());
        return errors;
    }
}
