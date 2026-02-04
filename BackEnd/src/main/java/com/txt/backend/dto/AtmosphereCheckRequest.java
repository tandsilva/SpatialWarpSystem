package com.txt.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtmosphereCheckRequest(
        @NotNull(message = "Oxygen level is required")
        @Min(value = 0, message = "Oxygen level must be positive")
        @Max(value = 100, message = "Oxygen level cannot exceed 100%")
        Double oxygenLevel,

        @NotNull(message = "CO2 level is required")
        @Min(value = 0, message = "CO2 level must be positive")
        @Max(value = 100, message = "CO2 level cannot exceed 100%")
        Double co2Level
) {}
