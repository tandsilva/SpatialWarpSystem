package com.txt.backend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    // Nome do Tópico para Telemetria da Nave
    public static final String TELEMETRY_TOPIC = "spaceship.telemetry.v1";

    @Bean
    public NewTopic telemetryTopic() {
        // Cria o tópico automaticamente se não existir.
        // partitions(3): Permite paralelismo (3 consumidores podem ler ao mesmo tempo)
        // replicas(1): Apenas 1 cópia dos dados (para dev/local)
        return TopicBuilder.name(TELEMETRY_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
