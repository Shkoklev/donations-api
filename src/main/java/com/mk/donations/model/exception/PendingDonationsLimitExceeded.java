package com.mk.donations.model.exception;

public class PendingDonationsLimitExceeded extends RuntimeException {
    public PendingDonationsLimitExceeded(String message) {
        super(message);
    }
}
