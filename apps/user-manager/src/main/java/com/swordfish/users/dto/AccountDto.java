package com.swordfish.users.dto;

import com.swordfish.users.validation.annotation.BirthDayValid;
import com.swordfish.users.validation.annotation.SexValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    @NotBlank(message = "username is not blank")
    @NotNull(message = "username is not null")
    @Size(min = 4, max = 20, message = "length of username must be between 4 to 20 characters")
    private String username;

    @NotBlank(message = "password is not blank")
    @NotNull(message = "password is not null")
    @Size(min = 6, max = 50, message = "length of password must be between 6 to 50 characters")
    private String password;

    @NotBlank(message = "full name is not blank")
    @NotNull(message = "full name is not null")
    @Size(min = 2, max = 30, message = "length of full name must be between 2 to 30 characters")
    private String fullName;

    @NotBlank(message = "birthday is not blank")
    @NotNull(message = "birthday is not null")
    @BirthDayValid
    private String dateOfBirth;

    @SexValid
    private String sex;
}
