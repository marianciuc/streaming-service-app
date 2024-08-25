package io.github.marianciuc.streamingservice.user.dto;


import io.github.marianciuc.streamingservice.user.enums.Role;

import java.util.UUID;

public record UserDetailsRequest(
        UUID userId,
        Role role
) {
}
