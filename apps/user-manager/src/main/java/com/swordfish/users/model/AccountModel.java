package com.swordfish.users.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("account")
public class AccountModel {

    private Long userId;

    private String username;

    private String password;
}
