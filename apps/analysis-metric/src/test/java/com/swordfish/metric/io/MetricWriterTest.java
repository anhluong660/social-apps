package com.swordfish.metric.io;

import com.swordfish.utils.enums.MetricTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class MetricWriterTest {

    @Autowired
    private MetricWriter metricWriter;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testMetricLog() throws InterruptedException {
        long current = System.currentTimeMillis();
        kafkaTemplate.send(MetricTopic.USER_MANAGER, current + "|123|Login|Welcome");
        kafkaTemplate.send(MetricTopic.MESSENGER, current + "|123|Chat|Hello");
        kafkaTemplate.send(MetricTopic.SOCIAL_SERVICE, current + "|123|Post|Feeling");
        Thread.sleep(2000);
    }
}
