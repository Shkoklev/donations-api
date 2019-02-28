package com.mk.donations.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, Long id) {
        super(entityType + "  со id : " + id + " не постои.");
    }

    public EntityNotFoundException(String entityType, String name) {
        super(entityType + " сo име : " + name + " не постои.");
    }
}