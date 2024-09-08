/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshTokenFactory.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;


import io.github.marianciuc.streamingservice.user.dto.Token;
import io.github.marianciuc.streamingservice.user.entity.User;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;

@Setter
public class RefreshTokenFactory implements AuthenticationTokenFactory {

    private Duration tokenTtl = Duration.ofDays(1);
    private String issuer = "streaming-service";

    /**
     * Generates a new refresh token based on the authentication object. The new token will have the same user id, subject, issuer.
     * The authorities will be mapped to add {@code "GRAND::"} prefix and add {@code "REFRESH::REFRESH_TOKEN"} role.
     * @param authentication the authentication object to be used to generate the refresh token
     * @return a new refresh token
     */
    @Override
    public Token apply(Authentication authentication) {
        UUID userId = UUID.randomUUID();
        if (authentication.getPrincipal() instanceof Token token) {
            userId = token.userId();
        } else if (authentication.getPrincipal() instanceof User user) {
            userId = user.getId();
        }
        LinkedList<String> authorities = authentication.getAuthorities().stream()
                .map(Object::toString).map("GRAND::"::concat).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        authorities.add("REFRESH::REFRESH_TOKEN");
        Instant now = Instant.now();

        return new Token(
                userId,
                UUID.randomUUID(),
                authentication.getName(),
                issuer,
                authorities,
                now,
                now.plus(this.tokenTtl)
        );
    }
}
