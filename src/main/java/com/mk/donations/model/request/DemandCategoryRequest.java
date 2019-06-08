package com.mk.donations.model.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DemandCategoryRequest {

    @NotNull(message = "Не смее да е празно !")
    @NotEmpty(message = "Не смее да е празно!")
    public String demandName;

    @NotNull(message = "Не смее да е празно !")
    @NotEmpty(message = "Не смее да е празно!")
    public String demandCategoryName;
}
