package com.mv.streamingservice.user.dto;

import com.mv.streamingservice.user.entity.Role;
import com.mv.streamingservice.user.entity.UserType;

import java.util.UUID;

public record UserRequest(
        UUID id,
        String username,
        String email,
        String securePassword,
        Role role,
        UserType userType
) {
}
