package com.swordfish.social.utils;

import com.swordfish.utils.common.RequestContextUtil;
import com.swordfish.utils.enums.MetricTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MetricUtils {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void log(Long userId, String action, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis()).append("|");
        sb.append(userId).append("|");
        sb.append(action).append("|");

        for (Object param : params) {
            sb.append(param).append("|");
        }

        sb.deleteCharAt(sb.length() - 1);

        kafkaTemplate.send(MetricTopic.SOCIAL_SERVICE, sb.toString());
    }

    public void log(String action, Object... params) {
        Long userId = RequestContextUtil.getUserId();
        log(userId, action, params);
    }
}
