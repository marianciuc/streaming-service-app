/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateEmployeeRequest.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.requests;

import io.github.marianciuc.streamingservice.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreateEmployeeRequest(
        @NotBlank(message = "Username is mandatory")
        @NotNull(message = "Username cannot be null")
        String username,

        @Email(message = "Proper email format required")
        String email,

        @NotNull(message = "Role cannot be null")
        Role role,

        @NotBlank(message = "Password is mandatory")
        @NotNull(message = "Password cannot be null")
        String password
) {
}
