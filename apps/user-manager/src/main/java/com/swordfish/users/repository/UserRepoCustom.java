package com.swordfish.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Long> findAllUserIds() {
        Query query = new Query();
        query.fields().include("userId").exclude("_id");

        record InnerUserId(long userId){}

        return mongoTemplate.find(query, InnerUserId.class, "user").stream()
                .map(InnerUserId::userId)
                .toList();
    }
}
