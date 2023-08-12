package com.sscn.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends IllegalArgumentException {

    public InvalidArgumentException(String message) {
        super(message);
    }

}
