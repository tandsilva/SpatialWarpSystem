package com.txt.backend.service;

import com.txt.backend.config.RabbitMQConfig;
import com.txt.backend.dto.SystemAlert;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CentralCommandListener {

    private final AIService aiService;

    public CentralCommandListener(AIService aiService) {
        this.aiService = aiService;
    }

    @RabbitListener(queues = RabbitMQConfig.CRITICAL_ALERTS_QUEUE)
    public void handleCriticalAlert(SystemAlert alert) {
        // This simulates the Main Bridge Console receiving the data
        System.out.println("==========================================");
        System.out.println(" [!] ALERT RECEIVED AT BRIDGE [!]");
        System.out.println(" SOURCE: " + alert.getSystemSource());
        System.out.println(" SEVERITY: " + alert.getSeverity());
        System.out.println(" MESSAGE: " + alert.getMessage());
        System.out.println("==========================================");

        // Automated Response Integration
        if ("CRITICAL".equals(alert.getSeverity())) {
            // AI takes over to coordinate repair automatically
            String action = aiService.coordinateDroidRepair("oxygen", 2); // Assuming 2 droids available standard
            System.out.println(" AI AUTO-RESPONSE: " + action);
        }
    }
}
