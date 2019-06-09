package com.mk.donations.model.request;

import javax.validation.constraints.NotNull;

public class QuantityRequest {

    @NotNull(message = "не смее да е празно")
    public Double quantity;
}
