package com.mv.streamingservice.user.dto;

import com.mv.streamingservice.user.entity.RecordStatus;
import com.mv.streamingservice.user.entity.Role;
import com.mv.streamingservice.user.entity.UserType;

import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String email,
        String passwordHash,
        RecordStatus recordStatus,
        UserType userType,
        Role role,
        Boolean isBanned
) {
}
