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
@Tag(name = "Telemetria e Sensores", description = "Endpoints para simular leituras de sensores e testar alertas via Kafka e RabbitMQ")
public class TelemetryController {

    private final AtmosphereService atmosphereService;
    private final KafkaProducerService kafkaProducerService;

    public TelemetryController(AtmosphereService atmosphereService, KafkaProducerService kafkaProducerService) {
        this.atmosphereService = atmosphereService;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Envia dados brutos de telemetria para o tópico do Kafka.
     * Útil para testar se o fluxo de mensagens está funcionando.
     */
    @PostMapping("/kafka/send")
    @Operation(summary = "Enviar Telemetria (Kafka)", description = "Envia um pacote de dados para o tópico Kafka 'spaceship.telemetry.v1'")
    public ResponseEntity<String> sendTelemetryToKafka(@RequestBody TelemetryData data) {
        if (data.getTimestamp() == null) {
            data.setTimestamp(LocalDateTime.now().toString());
        }

        kafkaProducerService.sendTelemetry(data);
        return ResponseEntity.ok("Telemetria enviada para o Kafka!");
    }

    /**
     * Simula a leitura de um sensor de oxigênio.
     * Se o nível estiver crítico (< 19.5%), dispara um alerta via RabbitMQ.
     */
    @PostMapping("/simulate/oxygen")
    @Operation(summary = "Injetar Leitura de Oxigênio", description = "Simula uma leitura de sensor. Se o nível for < 19.5%, dispara um alerta no RabbitMQ.")
    public ResponseEntity<String> simulateOxygenLevel(@RequestParam double oxygenPercentage) {
        
        // Chama a lógica de serviço.
        // Se oxigênio < 19.5, vai publicar no RabbitMQ.
        String status = atmosphereService.monitorOxygenLevel(oxygenPercentage, 0.04);
        
        return ResponseEntity.ok("Leitura do Sensor Processada. Status do Sistema: " + status);
    }
}
