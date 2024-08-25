package io.github.marianciuc.streamingservice.user.dto;

public record CredentialsRequest(
        String login,
        String password
) {
}
