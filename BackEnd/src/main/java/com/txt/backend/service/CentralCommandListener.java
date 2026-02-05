package com.txt.backend.service;

import com.txt.backend.config.RabbitMQConfig;
import com.txt.backend.dto.SystemAlert;
import com.txt.backend.model.AlertHistory;
import com.txt.backend.repository.AlertRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CentralCommandListener {

    private final AIService aiService;
    private final AlertRepository alertRepository;

    public CentralCommandListener(AIService aiService, AlertRepository alertRepository) {
        this.aiService = aiService;
        this.alertRepository = alertRepository;
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

        String actionTaken = "PENDING_MANUAL_REVIEW";

        // Automated Response Integration
        if ("CRITICAL".equals(alert.getSeverity())) {
            // AI takes over to coordinate repair automatically
            actionTaken = aiService.coordinateDroidRepair("oxygen", 2); 
            System.out.println(" AI AUTO-RESPONSE: " + actionTaken);
        }

        // Persist the event to the MySQL Black Box (Database)
        AlertHistory history = AlertHistory.builder()
                .systemSource(alert.getSystemSource())
                .severity(alert.getSeverity())
                .message(alert.getMessage())
                .timestamp(alert.getTimestamp())
                .automatedActionTaken(actionTaken)
                .build();
        
        alertRepository.save(history);
        System.out.println(" [✓] Alert verified and saved to flight log (MySQL).");
    }
}
