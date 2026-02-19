package com.txt.backend.controller;

import com.txt.backend.dto.TelemetryData;
import com.txt.backend.service.AtmosphereService;
import com.txt.backend.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/telemetry")
@Tag(name = "Telemetry and Sensors", description = "Endpoints to simulate sensor readings and test alerts via Kafka and RabbitMQ")
public class TelemetryController {

    private final AtmosphereService atmosphereService;
    private final KafkaProducerService kafkaProducerService;

    public TelemetryController(AtmosphereService atmosphereService, KafkaProducerService kafkaProducerService) {
        this.atmosphereService = atmosphereService;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
        * Sends raw telemetry data to the Kafka topic.
        * Useful for testing whether the message flow is working.
     */
    @PostMapping("/kafka/send")
        @Operation(summary = "Send Telemetry (Kafka)", description = "Sends a data packet to the Kafka topic 'spaceship.telemetry.v1'")
    public ResponseEntity<String> sendTelemetryToKafka(@RequestBody TelemetryData data) {
        if (data.getTimestamp() == null) {
            data.setTimestamp(LocalDateTime.now().toString());
        }

        kafkaProducerService.sendTelemetry(data);
        return ResponseEntity.ok("Telemetry sent to Kafka!");
    }

    /**
     * Simulates an oxygen sensor reading.
     * If the level is critical (< 19.5%), triggers an alert via RabbitMQ.
     */
    @PostMapping("/simulate/oxygen")
    @Operation(summary = "Inject Oxygen Reading", description = "Simulates a sensor reading. If the level is < 19.5%, triggers an alert in RabbitMQ.")
    public ResponseEntity<String> simulateOxygenLevel(@RequestParam double oxygenPercentage) {
        
        // Call the service logic.
        // If oxygen < 19.5, it will publish to RabbitMQ.
        String status = atmosphereService.monitorOxygenLevel(oxygenPercentage, 0.04);
        
        return ResponseEntity.ok("Sensor reading processed. System status: " + status);
    }
}
