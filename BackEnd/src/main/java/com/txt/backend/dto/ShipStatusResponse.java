package com.txt.backend.dto;

public record ShipStatusResponse(
        String crewStatus,
        String hullIntegrity,
        Double oxygenLevel,
        Double co2Level,
        Double energyConsumption,
        Double currentSpeed,
        Double maxSpeed,
        Boolean warpDriveActive,
        String atmosphereStatus,
        String energyStatus,
        String aiReport
) {}
