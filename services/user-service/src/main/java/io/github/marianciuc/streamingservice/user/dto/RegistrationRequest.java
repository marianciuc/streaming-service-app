package io.github.marianciuc.streamingservice.user.dto;

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
