/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RegistrationRequest.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.requests;

import io.github.marianciuc.streamingservice.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record class to represent the RegistrationRequest.
 */
public record RegistrationRequest(
        @NotBlank(message = "Username is mandatory") String username,
        @Email(message = "Email should be valid") String email,
        @NotBlank(message = "Password is mandatory") String password,
        @NotBlank Role role
) {
}
