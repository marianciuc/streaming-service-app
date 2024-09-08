/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JsonWebTokenResponse.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.responses;

public record JsonWebTokenResponse (
        String accessToken,
        String refreshToken
){
}
