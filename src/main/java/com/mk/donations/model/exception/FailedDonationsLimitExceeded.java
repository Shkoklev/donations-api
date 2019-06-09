package com.mk.donations.model.exception;

public class FailedDonationsLimitExceeded extends RuntimeException {
    public FailedDonationsLimitExceeded(String message) {
        super(message);
    }
}
