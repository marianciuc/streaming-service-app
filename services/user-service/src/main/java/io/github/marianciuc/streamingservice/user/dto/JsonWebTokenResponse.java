package com.mv.streamingservice.user.dto;

public record JsonWebTokenResponse (
        String accessToken,
        String refreshToken
){
}
