package com.txt.backend.service;

import com.txt.backend.model.WarpSimulation;
import com.txt.backend.repository.WarpSimulationRepository;
import com.txt.backend.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WarpSimulationService {

    private static final double SPEED_OF_LIGHT = 299792458; // Velocidade da luz em m/s
    private final WarpSimulationRepository warpSimulationRepository;

    public WarpSimulationService(WarpSimulationRepository warpSimulationRepository) {
        this.warpSimulationRepository = warpSimulationRepository;
    }

    // Simplified energy calculation based on bubble properties
    public double calculateEnergyRequired(double bubbleSize, double bubbleVelocity) {
        // Simplified formula based on energy-momentum tensor
        // E = integral T_00 dV
        return bubbleSize * bubbleVelocity * 1e12; // Arbitrary simulation value
    }

    // Method to simulate warp drive mechanics
    public WarpSimulation simulateWarp(double initialX, double initialY, double initialZ,
                                       double finalX, double finalY, double finalZ,
                                       double bubbleVelocity) {
        double distance = Math.sqrt(Math.pow(finalX - initialX, 2) +
                                   Math.pow(finalY - initialY, 2) +
                                   Math.pow(finalZ - initialZ, 2));

        double bubbleSize = distance / 10; // Bubble size proportional to distance
        double energyRequired = calculateEnergyRequired(bubbleSize, bubbleVelocity);
        double travelTime = distance / bubbleVelocity;

        WarpSimulation simulation = WarpSimulation.builder()
                .initialX(initialX)
                .initialY(initialY)
                .initialZ(initialZ)
                .finalX(finalX)
                .finalY(finalY)
                .finalZ(finalZ)
                .bubbleVelocity(bubbleVelocity)
                .bubbleSize(bubbleSize)
                .energyRequired(energyRequired)
                .warpPhase("START")
                .obstaclesDetected(false)
                .travelTime(travelTime)
                .build();

        return warpSimulationRepository.save(simulation);
    }

    public WarpSimulation getById(Long id) {
        return warpSimulationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warp simulation not found with id " + id));
    }

    public List<WarpSimulation> getAll() {
        return warpSimulationRepository.findAll();
    }

    // Method to verify if speed exceeds speed of light limit
    public boolean checkSpeedLimit(double currentSpeed) {
        return currentSpeed <= SPEED_OF_LIGHT;
    }

}