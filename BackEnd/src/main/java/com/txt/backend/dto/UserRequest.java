package com.txt.backend.dto;

import com.txt.backend.role.ContaminationStatus;
import com.txt.backend.role.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
        String password,

        @NotNull(message = "Role is required")
        Role role,

        boolean active,
        boolean blocked,

        @Size(min = 2, max = 100, message = "Crew name must be between 2 and 100 characters")
        String crew,

        @NotNull(message = "Contamination status is required")
        ContaminationStatus contaminationStatus
) {
}
