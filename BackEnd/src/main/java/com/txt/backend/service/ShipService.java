package com.txt.backend.service;


import com.txt.backend.dto.ShipStatusResponse;
import com.txt.backend.dto.WarpSimulationRequest;
import com.txt.backend.dto.warpSimulationResponse;
import com.txt.backend.mapper.WarpSimulationMapper;

import com.txt.backend.model.WarpSimulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing ship operations and status monitoring.
 * Coordinates atmosphere, energy, AI systems, and warp drive.
 */
@Service
public class ShipService {

    private static final Logger logger = LoggerFactory.getLogger(ShipService.class);
    private static final double SPEED_OF_LIGHT = 299792458.0; // m/s

    private final AtmosphereService atmosphereService;
    private final EnergyManagementService energyService;
    private final AIService aiService;
    private final WarpSimulationService warpService;

    public ShipService(AtmosphereService atmosphereService,
                       EnergyManagementService energyService,
                       AIService aiService,
                       WarpSimulationService warpService) {
        this.atmosphereService = atmosphereService;
        this.energyService = energyService;
        this.aiService = aiService;
        this.warpService = warpService;
    }

    /**
     * Get comprehensive ship status by aggregating data from all systems
     */
    public ShipStatusResponse getComprehensiveStatus(Double oxygenLevel, Double co2Level,
                                                     Double energyLevel, Boolean hullIntegrity,
                                                     Double currentSpeed) {
        logger.info("Retrieving comprehensive ship status");

        String atmosphereStatus = atmosphereService.monitorOxygenLevel(oxygenLevel, co2Level);
        String energyStatus = energyService.prioritizeSystems(energyLevel);
        String aiReport = aiService.detectFailures(oxygenLevel, energyLevel, hullIntegrity);
        String crewStatus = determineCrewStatus(oxygenLevel, hullIntegrity);

        return new ShipStatusResponse(
                crewStatus,
                hullIntegrity ? "INTACT" : "COMPROMISED",
                oxygenLevel,
                co2Level,
                calculateEnergyConsumption(currentSpeed),
                currentSpeed,
                SPEED_OF_LIGHT,
                isWarpDriveActive(currentSpeed),
                atmosphereStatus,
                energyStatus,
                aiReport
        );
    }

    /**
     * Simulate warp drive operation
     */
    public warpSimulationResponse simulateWarp(WarpSimulationRequest request) {
        logger.info("Simulating warp drive from ({},{},{}) to ({},{},{})",
                request.initialX(), request.initialY(), request.initialZ(),
                request.finalX(), request.finalY(), request.finalZ());
        
        WarpSimulation simulation = warpService.simulateWarp(
                request.initialX(),
                request.initialY(),
                request.initialZ(),
                request.finalX(),
                request.finalY(),
                request.finalZ(),
                request.bubbleVelocity()
        );
        
        return WarpSimulationMapper.toResponse(simulation);
    }

    /**
     * Check if current speed is within safe limits
     */
    public String checkSpeedLimit(Double currentSpeed) {
        logger.debug("Checking speed limit for speed: {}", currentSpeed);
        boolean withinLimit = warpService.checkSpeedLimit(currentSpeed);
        
        if (!withinLimit) {
            logger.warn("Speed {} exceeds light speed limit", currentSpeed);
            return "CRITICAL: Speed exceeds light speed! Immediate action required!";
        }
        
        double percentageOfLight = (currentSpeed / SPEED_OF_LIGHT) * 100;
        if (percentageOfLight > 90) {
            return String.format("WARNING: Approaching light speed (%.2f%% of c)", percentageOfLight);
        }
        
        return String.format("Speed within safe limits (%.2f%% of c)", percentageOfLight);
    }

    /**
     * Monitor atmosphere and provide recommendations
     */
    public String monitorAtmosphere(Double oxygenLevel, Double co2Level) {
        logger.debug("Monitoring atmosphere - O2: {}, CO2: {}", oxygenLevel, co2Level);
        return atmosphereService.monitorOxygenLevel(oxygenLevel, co2Level);
    }

    /**
     * Prioritize energy systems based on current levels
     */
    public String prioritizeEnergyUsage(Double currentEnergy) {
        logger.debug("Prioritizing energy systems with current level: {}", currentEnergy);
        return energyService.prioritizeSystems(currentEnergy);
    }

    /**
     * Run AI failure detection analysis
     */
    public String runFailureDetection(Double oxygenLevel, Double energyLevel, Boolean hullIntegrity) {
        logger.info("Running AI failure detection");
        return aiService.detectFailures(oxygenLevel, energyLevel, hullIntegrity);
    }

    // Private helper methods

    private String determineCrewStatus(Double oxygenLevel, Boolean hullIntegrity) {
        if (!hullIntegrity) {
            return "CRITICAL";
        }
        if (oxygenLevel < 19.5) {
            return "DANGER";
        }
        if (oxygenLevel < 20.0) {
            return "CAUTION";
        }
        return "NORMAL";
    }

    private Double calculateEnergyConsumption(Double currentSpeed) {
        // Base consumption + speed-based consumption
        double baseConsumption = 100.0;
        double speedFactor = (currentSpeed / SPEED_OF_LIGHT) * 10000;
        return baseConsumption + speedFactor;
    }

    private Boolean isWarpDriveActive(Double currentSpeed) {
        // Warp drive is considered active if speed > 10% light speed
        return currentSpeed > (SPEED_OF_LIGHT * 0.1);
    }
}
