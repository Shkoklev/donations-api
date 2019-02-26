package com.mk.donations.api.error;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


public class ApiError {

    private final int status;

    private final String error;

    private String message;

    public ApiError(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
