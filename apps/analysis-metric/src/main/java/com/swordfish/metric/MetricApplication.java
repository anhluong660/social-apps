package com.swordfish.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MetricApplication {

    private static final Logger log = LoggerFactory.getLogger(MetricApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MetricApplication.class);

        log.info("++++++++++++++++++++++++++++++++++++++");
        log.info("[[ Start Metric Application Success ]]");
        log.info("++++++++++++++++++++++++++++++++++++++");
    }
}
