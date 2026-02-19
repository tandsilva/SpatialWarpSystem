package com.txt.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txt.backend.config.KafkaConfig;
import com.txt.backend.dto.TelemetryData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * Service responsible for producing messages to Kafka.
 * Acts as a telemetry data publisher for the ship.
 */
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // Converts Java objects to JSON strings

    /**
     * Sends telemetry data to the Kafka topic.
     * 
     * @param data Object containing sensor data.
     */
    public void sendTelemetry(TelemetryData data) {
        try {
            // Convert TelemetryData object to a JSON string
            String jsonMessage = objectMapper.writeValueAsString(data);
            
            // Send to the topic defined in KafkaConfig.
            // The second parameter (data.getSensorId()) is the message KEY.
            // Using the same key ensures all messages from that sensor go to the same partition,
            // preserving read order.
            kafkaTemplate.send(KafkaConfig.TELEMETRY_TOPIC, data.getSensorId(), jsonMessage);
            
            log.info("Kafka Enviou [Sensor: {}]: {}", data.getSensorId(), jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar dados de telemetria", e);
        }
    }
}
