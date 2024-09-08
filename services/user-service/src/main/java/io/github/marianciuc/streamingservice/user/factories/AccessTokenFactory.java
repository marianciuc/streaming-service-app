/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AccessTokenFactory.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Setter
public class AccessTokenFactory implements TokenFactory {

    private Duration duration = Duration.ofMinutes(5);

    /**
     * Generates a new access token based on the refresh token. The new token will have the same user id, subject, issuer.
     * The authorities will be filtered to remove {@code "GRAND::"} prefix and remove roles with prefix {@code "REFRESH::"}.
     * The new token will have the current time as the issue time and the current time plus the duration as the expiry time.
     * @param token the refresh token to be used to generate the access token
     * @return a new access token
     */
    @Override
    public Token apply(Token token) {
        List<String> authorities = token.roles().stream()
                .map(s -> s.replace("GRAND::", ""))
                .filter(s -> !s.startsWith("REFRESH::"))
                .toList();
        Instant now = Instant.now();

        return new Token(
                token.userId(),
                token.userId(),
                token.subject(),
                token.issuer(),
                authorities,
                now,
                now.plus(this.duration)
        );
    }
}
