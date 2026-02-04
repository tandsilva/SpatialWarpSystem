package com.txt.backend.dto;

public record warpSimulationResponse(
        Long id,
        double initialX,
        double initialY,
        double initialZ,
        double finalX,
        double finalY,
        double finalZ,
        double bubbleVelocity,
        double bubbleSize,
        double energyRequired,
        String warpPhase,
        boolean obstaclesDetected,
        double travelTime
) {
}
