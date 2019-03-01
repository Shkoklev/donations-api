package com.mk.donations.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParameterMissingException extends RuntimeException {

    public ParameterMissingException(String message) {
        super(message);
    }

}

