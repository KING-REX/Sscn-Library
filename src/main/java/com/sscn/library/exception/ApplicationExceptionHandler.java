package com.sscn.library.exception;

import jakarta.json.JsonException;
import jakarta.json.stream.JsonParsingException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, String> handleAuthenticationExceptions(AuthenticationException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(CloneNotSupportedException.class)
    public Map<String, String> handleCloneNotSupportedExceptions(CloneNotSupportedException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationExceptions(ConstraintViolationException exception){
        Map<String, String> errors = new LinkedHashMap<>();

        String errorMessage = exception.getMessage();

        String mandatoryEmailError = "Email is mandatory!";
        String invalidEmailError = "Email is invalid!";
        String mandatoryPasswordError = "Password is mandatory!";
        String mandatoryISBNError = "ISBN is mandatory!";



        if(errorMessage.contains(mandatoryEmailError))
            errors.put("errorMessage", mandatoryEmailError);

        if(errorMessage.contains(invalidEmailError))
            errors.put("errorMessage", invalidEmailError);

        if(errorMessage.contains(mandatoryPasswordError))
            errors.put("errorMessage", mandatoryPasswordError);

        if(errorMessage.contains(mandatoryISBNError))
            errors.put("errorMessage", mandatoryISBNError);
        errors.put("errorType", exception.getClass().toString());

        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolationExceptions(DataIntegrityViolationException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        errors.put("errorMostSpecificCause", exception.getMostSpecificCause().getMessage());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateValueException.class)
    public Map<String, String> handleDuplicateValueExceptions(DuplicateValueException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public Map<String, String> handleHttpMediaTypeNotAcceptableExceptions(HttpMediaTypeNotAcceptableException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        errors.put("supportedTypes", exception.getSupportedMediaTypes().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> handleHttpRequestMethodNotSupportedExceptions(HttpRequestMethodNotSupportedException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        errors.put("supportedMethods", Arrays.toString(exception.getSupportedMethods()));
        errors.put("supportedHttpMethods", Objects.requireNonNull(exception.getSupportedHttpMethods()).toString());
        exception.getStatusCode();
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleIllegalStateExceptions(IllegalStateException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidArgumentException.class)
    public Map<String, String> handleInvalidArgumentExceptions(InvalidArgumentException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentExceptions(IllegalArgumentException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Map<String, String> handleInternalAuthenticationServiceExceptions(InternalAuthenticationServiceException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(JpaSystemException.class)
    public Map<String, String> handleJpaSystemExceptions(JpaSystemException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        errors.put("errorMostSpecificCause", exception.getMostSpecificCause().getMessage());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(JsonException.class)
    public Map<String, String> handleJsonExceptions(JsonException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(JsonParsingException.class)
    public Map<String, String> handleJsonParsingExceptions(JsonParsingException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("errorMessage", errorMessage);
            errors.put("errorField", fieldName);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundExceptions(NotFoundException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        System.out.println(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleSQLIntegrityConstraintViolationExceptions(SQLIntegrityConstraintViolationException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        errors.put("errorState", exception.getSQLState());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(UnsupportedOperationException.class)
    public Map<String, String> handleUnsupportedOperationExceptions(UnsupportedOperationException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUsernameNotFoundExceptions(UsernameNotFoundException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }









    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeExceptions(RuntimeException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorMessage", exception.getMessage());
        errors.put("errorType", exception.getClass().toString());
        return errors;
    }
}
