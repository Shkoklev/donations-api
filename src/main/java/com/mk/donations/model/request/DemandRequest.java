package com.mk.donations.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DemandRequest {

    @NotNull(message = "не смее да е празно !")
    @NotEmpty(message = "не смее да е празно !")
    @Pattern(message = "мора да содржи само мали кирилични букви", regexp = "^[а-шѓѕјќџ]+$")
    public String name;

    public String category;

    public String unitName;
}
