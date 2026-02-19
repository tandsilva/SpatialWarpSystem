package com.txt.backend.service;

import com.txt.backend.config.RabbitMQConfig;
import com.txt.backend.dto.SystemAlert;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!dev") // Real RabbitMQ only outside 'dev'
public class RabbitMQAlertSender implements AlertSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQAlertSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendCriticalAlert(SystemAlert alert) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TELEMETRY_EXCHANGE,
                "alert.critical.oxygen",
                alert
        );
    }
}
