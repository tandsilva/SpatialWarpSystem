package com.txt.backend.service;

import org.springframework.stereotype.Service;

@Service
public class EnergyManagementService {

    // Method to prioritize critical systems post-warp
    public String prioritizeSystems(double currentEnergy) {
        if (currentEnergy < 10) {
            return "CRITICAL: Energy low. Prioritizing life support, navigation, and communication.";
        } else if (currentEnergy < 30) {
            return "WARNING: Energy moderate. Maintaining essential systems, suspending non-critical.";
        } else {
            return "NORMAL: Energy sufficient. All systems operational.";
        }
    }

    // Method to calculate solar panel energy recovery
    public double calculateSolarRecovery(double solarIntensity, double panelEfficiency) {
        // Simplified Formula: Energy = Intensity * Efficiency * Area (assuming 100m²)
        return solarIntensity * panelEfficiency * 100;
    }

    // Method to simulate energy consumption
    public double simulateEnergyConsumption(boolean warpActive, boolean gravityActive,
                                           boolean atmosphereActive, int crewSize) {
        double baseConsumption = 100; // Consumo base em kW
        double warpConsumption = warpActive ? 10000 : 0;
        double gravityConsumption = gravityActive ? 500 : 0;
        double atmosphereConsumption = atmosphereActive ? 200 : 0;
        double crewConsumption = crewSize * 10; // Consumo por tripulação

        return baseConsumption + warpConsumption + gravityConsumption + atmosphereConsumption + crewConsumption;
    }

}