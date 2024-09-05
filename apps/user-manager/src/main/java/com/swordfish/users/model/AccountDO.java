package com.swordfish.users.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("account")
public class AccountDO {

    private String username;

    private String password;

    private String fullName;

    private LocalDate dateOfBirth;

    private String sex;
}
