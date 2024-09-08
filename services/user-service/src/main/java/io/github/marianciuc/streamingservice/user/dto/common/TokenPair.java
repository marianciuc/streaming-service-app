/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenPair.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.common;

public record TokenPair(
        String accessToken,
        String accessTokenExpiry,
        String refreshToken,
        String refreshTokenExpiry
) {
}
