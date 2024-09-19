package com.swordfish.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UserManagerApplication {

    private static final Logger log = LoggerFactory.getLogger(UserManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserManagerApplication.class, args);

        log.info("++++++++++++++++++++++++++++++++++++++++++++");
        log.info("[[ Start User Manager Application Success ]]");
        log.info("++++++++++++++++++++++++++++++++++++++++++++");
    }
}
