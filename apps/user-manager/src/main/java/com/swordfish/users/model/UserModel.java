package com.swordfish.users.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("user")
public class UserModel {

    private Long userId;

    private String nickName;

    private String avatarUrl;

    private Date dateOfBirth;

    private String sex;

    private List<Long> friends;

    /**
     * other users send invitation to me
     */
    private List<Long> inviters;

    /**
     * I send invitation to other users
     */
    private List<Long> requests;
}
