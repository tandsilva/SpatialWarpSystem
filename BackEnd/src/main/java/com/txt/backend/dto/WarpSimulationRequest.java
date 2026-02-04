package com.txt.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WarpSimulationRequest(
        @NotNull(message = "Initial X coordinate is required")
        Double initialX,

        @NotNull(message = "Initial Y coordinate is required")
        Double initialY,

        @NotNull(message = "Initial Z coordinate is required")
        Double initialZ,

        @NotNull(message = "Final X coordinate is required")
        Double finalX,

        @NotNull(message = "Final Y coordinate is required")
        Double finalY,

        @NotNull(message = "Final Z coordinate is required")
        Double finalZ,

        @NotNull(message = "Bubble velocity is required")
        @Min(value = 1, message = "Bubble velocity must be positive")
        Double bubbleVelocity
) {
}
