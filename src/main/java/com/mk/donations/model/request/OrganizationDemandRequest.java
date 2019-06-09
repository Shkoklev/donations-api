package com.mk.donations.model.request;

import com.mk.donations.model.Unit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrganizationDemandRequest {

    @NotNull(message = "Не смее да е празно !")
    @NotEmpty(message = "Не смее да е празно!")
    public String demandName;

    @NotNull(message = "Не смее да е празно !")
    public Double quantity;

}
