package com.mk.donations.model.request;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditDonorRequest {

    public String firstName;

    public String lastName;

    public String email;

    public String password;

    public String phone;

    public String pictureUrl;
}
