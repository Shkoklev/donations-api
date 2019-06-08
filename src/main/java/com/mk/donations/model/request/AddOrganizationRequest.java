package com.mk.donations.model.request;

import javax.validation.constraints.*;

public class AddOrganizationRequest {

    @NotNull(message = "Името не смее да е празно !")
    @NotEmpty(message = "Името не смее да е празно !")
    public String name;

    @NotNull(message = "Телефонскиот број не смее да е празен !")
    @NotEmpty(message = "Телефонскиот број не смее да е празен !")
    @Pattern(message = "мора да содржи само бројки", regexp = "^[0-9]+$")
    public String phone;

    @NotNull(message = "Внесете Е-mail !")
    @NotEmpty(message = "Внесете Е-mail !")
    @Email(message = "Невалиден формат на адреса")
    public String email;

    @NotNull(message = "Внесете пасворд !")
    @NotEmpty(message = "Внесете пасворд !")
    @Size(min = 8, max = 100, message = "Пасвордот мора да содржи барем 8 карактери")
    public String password;

    @NotNull(message = "Внесете категорија !")
    @NotEmpty(message = "Внесете категорија !")
    public String category;

}
