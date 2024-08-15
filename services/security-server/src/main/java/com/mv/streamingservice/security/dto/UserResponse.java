package com.mv.streamingservice.security.dto;

import com.mv.streamingservice.security.entity.RecordStatus;
import com.mv.streamingservice.security.entity.Role;
import com.mv.streamingservice.security.entity.UserType;

import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String username,
        String email,
        String passwordHash,
        RecordStatus recordStatus,
        UserType userType,
        Role role,
        Boolean isBanned
) {
}
