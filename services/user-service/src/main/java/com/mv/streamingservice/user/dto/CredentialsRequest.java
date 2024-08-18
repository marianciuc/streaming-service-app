package com.mv.streamingservice.user.dto;

public record CredentialsRequest(
        String login,
        String password
) {
}
