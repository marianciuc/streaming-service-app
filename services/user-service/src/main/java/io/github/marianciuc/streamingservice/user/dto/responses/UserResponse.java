/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserResponse.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.responses;


import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.RecordStatus;
import io.github.marianciuc.streamingservice.user.enums.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        RecordStatus recordStatus,
        Role role,
        Boolean isBanned
) {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRecordStatus(),
                Role.valueOf(user.getRole()),
                user.getIsBanned()
        );
    }
}
