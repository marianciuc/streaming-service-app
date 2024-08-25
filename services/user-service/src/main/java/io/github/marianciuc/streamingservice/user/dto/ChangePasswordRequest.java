package com.mv.streamingservice.user.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
