package com.txt.backend.service;

import com.txt.backend.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
/**
 * Serviço responsável por consumir (ler) mensagens do Kafka.
 * Simula o painel de controle da missão recebendo os dados em tempo real.
 */
public class KafkaConsumerService {

    // groupId distingue quem está lendo. Vários serviços podem ler o mesmo tópico se tiverem groupIds diferentes.
    // Se outro serviço usar o mesmo groupId, o Kafka divide a carga (balanceamento).
    @KafkaListener(topics = KafkaConfig.TELEMETRY_TOPIC, groupId = "mission-control-dashboard")
    public void listen(String message) {
        // Aqui chega a string JSON crua. Em um sistema real, converteríamos de volta para Objeto (Deserialização).
        log.info("Kafka Recebeu: {}", message);
        
        // Simulação de processamento (ex: salvar no banco, atualizar dashboard via WebSocket)
        processTelemetry(message);
    }

    private void processTelemetry(String data) {
        // Lógica de negócio aqui...
        // log.debug("Processando telemetria em background...");
    }
}
