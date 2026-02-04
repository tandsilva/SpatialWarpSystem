package com.txt.backend.mapper;

import com.txt.backend.dto.QuarantineRequest;
import com.txt.backend.dto.QuarantineResponse;
import com.txt.backend.model.Quarantine;
import com.txt.backend.model.User;
import java.util.List;

public final class QuarantineMapper {

    private QuarantineMapper() {
    }

    public static Quarantine toEntity(QuarantineRequest request) {
        return Quarantine.builder()
                .codeNumber(request.codeNumber())
                .labId(request.labId())
                .protocol(request.protocol())
                .reason(request.reason())
                .active(request.active())
                .nonInterruptible(true)
                .build();
    }

    public static QuarantineResponse toResponse(Quarantine quarantine) {
        List<Long> userIds = quarantine.getUsers() == null
                ? List.of()
                : quarantine.getUsers().stream().map(User::getId).toList();

        return new QuarantineResponse(
                quarantine.getCodeNumber(),
                quarantine.getLabId(),
                quarantine.getProtocol(),
                quarantine.getReason(),
                quarantine.isActive(),
                quarantine.isNonInterruptible(),
                userIds
        );
    }
}
