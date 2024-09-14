package com.swordfish.users.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedissonClient redissonClient;

    public <T> T synchronize(Callable<T> task, String redisKey) {
        RLock lock = redissonClient.getLock(redisKey);

        try {
            boolean locked = lock.tryLock(50, 10, TimeUnit.SECONDS);

            if (locked) {
                try {
                    return task.call();
                } finally {
                    lock.unlock();
                }
            } else {
                log.warn("RedisUtils.synchronize task is not execute");
                return null;
            }

        } catch (Exception ex) {
            log.error("Exception RedisUtils.synchronize: {}", ex.getMessage());
            return null;
        }
    }

    public <T> T syncSingle(Callable<T> task, String redisKey) {
        RLock lock = redissonClient.getLock(redisKey);

        if (lock.tryLock()) {
            try {
                return task.call();
            } catch (Exception ex) {
                log.error("Exception RedisUtils.syncSingle: {}", ex.getMessage());
            } finally {
                lock.unlock();
            }
        }

        return null;
    }
}
