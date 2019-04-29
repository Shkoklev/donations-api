package com.mk.donations.model.request;

import javax.validation.constraints.Pattern;

public class OrganizationCategoryRequest {

    @Pattern(message = "мора да содржи само мали кирилични букви", regexp = "^[а-ш]+$")
    public String name;

    public String pictureUrl;

}
