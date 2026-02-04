package com.txt.backend.mapper;

import com.txt.backend.dto.warpSimulationResponse;
import com.txt.backend.model.WarpSimulation;

public final class WarpSimulationMapper {

    private WarpSimulationMapper() {
    }

    public static warpSimulationResponse toResponse(WarpSimulation simulation) {
        return new warpSimulationResponse(
                simulation.getId(),
                simulation.getInitialX(),
                simulation.getInitialY(),
                simulation.getInitialZ(),
                simulation.getFinalX(),
                simulation.getFinalY(),
                simulation.getFinalZ(),
                simulation.getBubbleVelocity(),
                simulation.getBubbleSize(),
                simulation.getEnergyRequired(),
                simulation.getWarpPhase(),
                simulation.isObstaclesDetected(),
                simulation.getTravelTime()
        );
    }
}
