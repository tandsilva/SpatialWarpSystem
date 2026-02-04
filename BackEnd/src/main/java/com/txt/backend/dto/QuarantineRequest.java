package com.txt.backend.dto;

import com.txt.backend.role.EmergencyProtocol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record QuarantineRequest(
        @NotBlank(message = "Code number is required")
        @Size(min = 3, max = 50, message = "Code number must be between 3 and 50 characters")
        String codeNumber,

        @NotBlank(message = "Lab ID is required")
        @Size(min = 2, max = 50, message = "Lab ID must be between 2 and 50 characters")
        String labId,

        @NotNull(message = "Protocol is required")
        EmergencyProtocol protocol,

        @NotBlank(message = "Reason is required")
        @Size(min = 10, max = 500, message = "Reason must be between 10 and 500 characters")
        String reason,

        boolean active,

        @Size(min = 1, message = "At least one user must be assigned to quarantine")
        List<Long> userIds
) {
}
