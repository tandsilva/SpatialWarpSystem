package com.txt.backend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    // Topic name for ship telemetry
    public static final String TELEMETRY_TOPIC = "spaceship.telemetry.v1";

    @Bean
    public NewTopic telemetryTopic() {
        // Creates the topic automatically if it does not exist.
        // partitions(3): Enables parallelism (3 consumers can read at the same time)
        // replicas(1): Only 1 data copy (for dev/local)
        return TopicBuilder.name(TELEMETRY_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
