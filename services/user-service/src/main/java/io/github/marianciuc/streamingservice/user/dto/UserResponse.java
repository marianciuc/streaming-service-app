package com.mv.streamingservice.user.dto;

import com.mv.streamingservice.user.enums.RecordStatus;
import com.mv.streamingservice.user.enums.Role;
import com.mv.streamingservice.user.enums.UserType;

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
