package com.txt.backend.dto;

import com.txt.backend.role.EmergencyProtocol;
import java.util.List;

public record QuarantineResponse(
        String codeNumber,
        String labId,
        EmergencyProtocol protocol,
        String reason,
        boolean active,
        boolean nonInterruptible,
        List<Long> userIds
) {
}
