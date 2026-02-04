package com.txt.backend.dto;

import com.txt.backend.role.ContaminationStatus;
import com.txt.backend.role.Role;

public record UserResponse(
        Long id,
        String name,
        Role role,
        boolean active,
        boolean blocked,
        String crew,
        ContaminationStatus contaminationStatus
) {
}
