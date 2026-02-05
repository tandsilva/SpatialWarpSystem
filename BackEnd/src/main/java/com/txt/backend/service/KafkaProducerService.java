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
 * Serviço responsável por produzir mensagens para o Kafka.
 * Atua como um "Emissor" de dados de telemetria da nave.
 */
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // Para converter objeto Java em JSON String

    /**
     * Envia os dados de telemetria para o tópico do Kafka.
     * 
     * @param data Objeto contendo os dados do sensor.
     */
    public void sendTelemetry(TelemetryData data) {
        try {
            // Converte o objeto TelemetryData para uma String JSON
            String jsonMessage = objectMapper.writeValueAsString(data);
            
            // Envia para o tópico definido em KafkaConfig. 
            // O segundo parâmetro (data.getSensorId()) é a CHAVE (Key) da mensagem.
            // Usar a mesma chave garante que todas as mensagens desse sensor vão para a mesma partição,
            // mantendo a ordem correta de leitura.
            kafkaTemplate.send(KafkaConfig.TELEMETRY_TOPIC, data.getSensorId(), jsonMessage);
            
            log.info("Kafka Enviou [Sensor: {}]: {}", data.getSensorId(), jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar dados de telemetria", e);
        }
    }
}
