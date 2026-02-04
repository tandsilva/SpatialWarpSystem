package com.txt.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SpeedCheckRequest(
        @NotNull(message = "Current speed is required")
        @Min(value = 0, message = "Current speed cannot be negative")
        Double currentSpeed
) {}
