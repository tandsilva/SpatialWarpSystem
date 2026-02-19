package com.txt.backend.service;

import com.txt.backend.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
/**
 * Service responsible for consuming (reading) Kafka messages.
 * Simulates the mission control dashboard receiving real-time data.
 */
public class KafkaConsumerService {

    // groupId identifies who is reading. Multiple services can read the same topic if they use different groupIds.
    // If another service uses the same groupId, Kafka splits the load (balancing).
    @KafkaListener(topics = KafkaConfig.TELEMETRY_TOPIC, groupId = "mission-control-dashboard")
    public void listen(String message) {
        // Raw JSON string arrives here. In a real system, we would convert it back to an object (deserialization).
        log.info("Kafka Recebeu: {}", message);
        
        // Processing simulation (e.g., save to DB, update dashboard via WebSocket)
        processTelemetry(message);
    }

    private void processTelemetry(String data) {
        // Business logic here...
        // log.debug("Processing telemetry in background...");
    }
}
