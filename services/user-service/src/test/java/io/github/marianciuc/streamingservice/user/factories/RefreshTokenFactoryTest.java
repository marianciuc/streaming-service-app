/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshTokenFactoryTest.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.entity.JWTUserPrincipal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RefreshTokenFactoryTest {

    @Test
    public void apply_whenUserPrincipalExists_returnsToken() {
        // Arrange
        JWTUserPrincipal jwtUserPrincipal = new JWTUserPrincipal(
                "username",
                "password",
                true,
                true,
                true,
                true,
                List.of("ROLE").stream().map(SimpleGrantedAuthority::new).toList(),
                new Token(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "username",
                        "issuer",
                        List.of("ROLE"),
                        Instant.now(),
                        Instant.now()
                ), UUID.randomUUID()
        );
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(jwtUserPrincipal, null, authorities);
        RefreshTokenFactory refreshTokenFactory = new RefreshTokenFactory();
        refreshTokenFactory.issuer("test-issuer");
        refreshTokenFactory.tokenTtl(Duration.ofDays(1));

        // Act
        Token token = refreshTokenFactory.apply(authentication);

        // Assert
        assertNotNull(token);
        assertEquals(token.userId(), jwtUserPrincipal.getId());
        assertEquals(token.subject(), jwtUserPrincipal.getUsername());
        assertEquals(token.issuer(), "test-issuer");
        assertEquals(token.roles().get(0), "GRAND::ROLE_USER");
        assertEquals(token.createdAt().isBefore(Instant.now()), true);
        assertEquals(token.expiresAt().isAfter(Instant.now()), true);
    }

    @Test
    public void apply_whenUserPrincipalNotExists_returnsNull() {
        // Arrange
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(null, null, authorities);
        RefreshTokenFactory refreshTokenFactory = new RefreshTokenFactory();

        // Act
        Token token = refreshTokenFactory.apply(authentication);

        // Assert
        assertNull(token);
    }
}