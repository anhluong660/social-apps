package com.swordfish.metric.service;

import com.swordfish.metric.io.MetricWriter;
import com.swordfish.utils.enums.MetricTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    @Autowired
    private MetricWriter metricWriter;

    @KafkaListener(topics = {MetricTopic.USER_MANAGER, MetricTopic.MESSENGER, MetricTopic.SOCIAL_SERVICE})
    public void listenMetricLog(String line) {
        metricWriter.log(line);
    }
}
