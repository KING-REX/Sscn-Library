package com.sscn.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException(String message) {
        super (message);
        //TODO: Find a way to display the error message to the frontend, not just a "Bad Request"!
    }


}
