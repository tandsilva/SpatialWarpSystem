package com.txt.backend.service;

import com.txt.backend.config.RabbitMQConfig;
import com.txt.backend.dto.SystemAlert;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AtmosphereService {

    private static final double IDEAL_OXYGEN_LEVEL = 20.5; // Ideal Oxygen %
    private static final double CRITICAL_OXYGEN_LEVEL = 19.5; // Critical Oxygen %

    private final RabbitTemplate rabbitTemplate;

    public AtmosphereService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Method to monitor oxygen levels
    public String monitorOxygenLevel(double currentOxygenLevel, double co2Level) {
        if (currentOxygenLevel < CRITICAL_OXYGEN_LEVEL) {
            // FIRE AND FORGET: Async notification
            SystemAlert alert = SystemAlert.builder()
                    .systemSource("LIFE_SUPPORT")
                    .severity("CRITICAL")
                    .message("Oxygen level hypoxic: " + currentOxygenLevel + "%")
                    .timestamp(LocalDateTime.now())
                    .build();
            
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TELEMETRY_EXCHANGE, 
                    "alert.critical.oxygen", 
                    alert
            );

            return "CRITICAL: Oxygen level too low! Activating emergency protocols.";
        } else if (currentOxygenLevel < IDEAL_OXYGEN_LEVEL) {
            return "WARNING: Oxygen level below ideal. Adjusting atmosphere generator.";
        } else {
            return "NORMAL: Oxygen level stable.";
        }
    }

    // Method to calculate required oxygen production
    public double calculateOxygenProduction(int crewSize, double currentOxygenLevel) {
        // Approximate consumption: 0.5 L/min per person
        double consumptionRate = crewSize * 0.5;
        double deficit = IDEAL_OXYGEN_LEVEL - currentOxygenLevel;
        return consumptionRate + (deficit > 0 ? deficit * 10 : 0); // Simplified adjustment
    }

    // Method to check atmosphere generator status
    public String checkAtmosphereGenerator(boolean isActive, double efficiency) {
        if (!isActive) {
            return "ERROR: Atmosphere generator is inactive!";
        } else if (efficiency < 80) {
            return "WARNING: Atmosphere generator efficiency low.";
        } else {
            return "OK: Atmosphere generator operating normally.";
        }
    }

}