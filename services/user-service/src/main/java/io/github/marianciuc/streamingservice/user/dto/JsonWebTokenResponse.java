package io.github.marianciuc.streamingservice.user.dto;

public record JsonWebTokenResponse (
        String accessToken,
        String refreshToken
){
}
