package com.txt.backend.service;

import com.txt.backend.dto.SystemAlert;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class ConsoleAlertSender implements AlertSender {

    @Override
    public void sendCriticalAlert(SystemAlert alert) {
        System.out.println("=============================================");
        System.out.println(" [MOCK RABBITMQ] Sending Alert to Console:");
        System.out.println(" SOURCE: " + alert.getSystemSource());
        System.out.println(" MESSAGE: " + alert.getMessage());
        System.out.println(" SEVERITY: " + alert.getSeverity());
        System.out.println("=============================================");
    }
}
