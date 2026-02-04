package com.txt.backend.mapper;

import com.txt.backend.dto.UserRequest;
import com.txt.backend.dto.UserResponse;
import com.txt.backend.model.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserRequest request) {
        return User.builder()
                .name(request.name())
                .password(request.password())
                .role(request.role())
                .isActive(request.active())
                .isBlocked(request.blocked())
                .crew(request.crew())
                .contaminationStatus(request.contaminationStatus())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.isActive(),
                user.isBlocked(),
                user.getCrew(),
                user.getContaminationStatus()
        );
    }
}
