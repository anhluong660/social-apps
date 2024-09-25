package com.swordfish.metric.config;

import com.swordfish.utils.enums.MetricTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic createUserManagerTopic() {
        return TopicBuilder.name(MetricTopic.USER_MANAGER)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic createMessengerTopic() {
        return TopicBuilder.name(MetricTopic.MESSENGER)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic createSocialServiceTopic() {
        return TopicBuilder.name(MetricTopic.SOCIAL_SERVICE)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
