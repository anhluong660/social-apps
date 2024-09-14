package com.swordfish.users.utils;

import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserIdGenerator {

    @Value("${account.userId}")
    private Long USER_ID_DEFAULT;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Long userId = null;

    public synchronized Long getNewId() {
        if (userId == null) {
            Document document = mongoTemplate.getCollection("account")
                    .find()
                    .sort(Sorts.descending("userId"))
                    .first();

            if (document != null) {
                userId = document.getLong("userId");
            } else {
                userId = USER_ID_DEFAULT;
            }
        }

        userId += 1;

        return userId;
    }
}
