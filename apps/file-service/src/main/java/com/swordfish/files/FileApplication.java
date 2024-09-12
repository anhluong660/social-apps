package com.swordfish.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FileApplication {

    private static final Logger log = LoggerFactory.getLogger(FileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);

        log.info("++++++++++++++++++++++++++++++++++++");
        log.info("[[ Start File Application Success ]]");
        log.info("++++++++++++++++++++++++++++++++++++");
    }
}
