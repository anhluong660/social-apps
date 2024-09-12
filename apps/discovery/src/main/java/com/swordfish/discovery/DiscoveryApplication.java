package com.swordfish.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class);

        log.info("+++++++++++++++++++++++++++++++++++++++++");
        log.info("[[ Start Discovery Application Success ]]");
        log.info("+++++++++++++++++++++++++++++++++++++++++");
    }
}
