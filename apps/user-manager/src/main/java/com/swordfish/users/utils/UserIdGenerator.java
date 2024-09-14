package com.swordfish.users.utils;

import com.mongodb.client.model.Sorts;
import com.swordfish.utils.enums.RedisKey;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtils redisUtils;

    @PostConstruct
    public int init() {
        RAtomicLong atomicIds = redissonClient.getAtomicLong(RedisKey.USER_ID);
        if (atomicIds.isExists())  {
            return 0;
        }

        Document document = mongoTemplate.getCollection("account")
                .find()
                .sort(Sorts.descending("userId"))
                .first();

        long userId;

        if (document != null) {
            userId = document.getLong("userId");
        } else {
            userId = USER_ID_DEFAULT;
        }

        atomicIds.set(userId + 1);
        return 1;
    }

    public Long getNewId() {
        RAtomicLong atomicIds = redissonClient.getAtomicLong(RedisKey.USER_ID);
        long id = atomicIds.getAndIncrement();

        if (id <= USER_ID_DEFAULT) {
            atomicIds.delete();
            redisUtils.synchronize(this::init, RedisKey.SYNC_USER_ID);
            throw new RuntimeException("Generate UserId Error");
        }

        return id;
    }
}
