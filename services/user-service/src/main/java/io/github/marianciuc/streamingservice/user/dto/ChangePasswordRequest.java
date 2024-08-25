package io.github.marianciuc.streamingservice.user.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
