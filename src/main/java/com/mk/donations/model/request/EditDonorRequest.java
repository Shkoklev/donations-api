package com.mk.donations.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditDonorRequest {

    public String firstName;

    public String lastName;

    @Email
    public String email;

    @Size(min = 8, max = 30, message = "Пасвордот мора да содржи барем 8 карактери")
    public String password;

    @Pattern(message = "мора да содржи само бројки", regexp = "^[0-9]+$")
    public String phone;

    public String pictureUrl;
}
