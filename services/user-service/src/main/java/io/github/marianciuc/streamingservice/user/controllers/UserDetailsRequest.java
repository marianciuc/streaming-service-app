package com.mv.streamingservice.user.controllers;

import com.mv.streamingservice.user.enums.Role;

import java.util.UUID;

public record UserDetailsRequest(
        UUID userId,
        Role role
) {
}
