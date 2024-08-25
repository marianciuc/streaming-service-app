package io.github.marianciuc.streamingservice.user.dto;


import io.github.marianciuc.streamingservice.user.enums.RecordStatus;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.enums.UserType;

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
