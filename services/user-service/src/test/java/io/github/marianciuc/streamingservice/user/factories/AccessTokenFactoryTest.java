/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AccessTokenFactoryTest.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AccessTokenFactoryTest {

    @InjectMocks
    AccessTokenFactory accessTokenFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_token_apply_success() {
        UUID tokenId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String subject = "tokenSubject";
        String issuer = "tokenIssuer";
        List<String> roles = Arrays.asList("GRAND::Admin", "Operator", "REFRESH::Guest");
        Instant createdAt = Instant.now();
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(5));

        Token token = new Token(tokenId, userId, subject, issuer, roles, createdAt, expiresAt);

        accessTokenFactory.setDuration(Duration.ofMinutes(10));
        Token newToken = accessTokenFactory.apply(token);

        Assertions.assertEquals(tokenId, newToken.tokenId());
        Assertions.assertEquals(userId, newToken.userId());
        Assertions.assertEquals(subject, newToken.subject());
        Assertions.assertEquals(issuer, newToken.issuer());
        Assertions.assertEquals(2, newToken.roles().size());
    }
}