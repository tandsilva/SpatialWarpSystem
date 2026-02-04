package com.txt.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ShipStatusRequest(
        @NotNull(message = "Oxygen level is required")
        @Min(value = 0, message = "Oxygen level must be positive")
        @Max(value = 100, message = "Oxygen level cannot exceed 100%")
        Double oxygenLevel,

        @NotNull(message = "CO2 level is required")
        @Min(value = 0, message = "CO2 level must be positive")
        @Max(value = 100, message = "CO2 level cannot exceed 100%")
        Double co2Level,

        @NotNull(message = "Energy level is required")
        @Min(value = 0, message = "Energy level must be positive")
        Double energyLevel,

        @NotNull(message = "Hull integrity is required")
        Boolean hullIntegrity,

        @NotNull(message = "Current speed is required")
        @Min(value = 0, message = "Current speed cannot be negative")
        Double currentSpeed
) {}
