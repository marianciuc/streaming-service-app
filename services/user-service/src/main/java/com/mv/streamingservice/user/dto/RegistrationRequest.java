package com.mv.streamingservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
        @NotBlank(message = "Username is mandatory")
        String username,

        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password
) {
}
