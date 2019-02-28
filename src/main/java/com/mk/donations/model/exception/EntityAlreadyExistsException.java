package com.mk.donations.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String entityType, Long id) {
        super(entityType + " со id : " + id + " веќе постои.");
    }

    public EntityAlreadyExistsException(String entityType, String name) {
        super(entityType + "  со име : " + name + " веќе постои.");
    }


}
