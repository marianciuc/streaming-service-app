/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JwtAuthenticationConvertor.java
 *
 */

package io.github.marianciuc.streamingservice.user.security;

import io.github.marianciuc.streamingservice.user.dto.Token;
import io.github.marianciuc.streamingservice.user.serializers.TokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class JWTAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer accessTokenStringDeserializer;
    private final TokenDeserializer refreshTokenStringDeserializer;

    public JWTAuthenticationConverter(TokenDeserializer accessTokenStringDeserializer, TokenDeserializer refreshTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Token accessToken = this.accessTokenStringDeserializer.apply(token);
            if (accessToken != null) {
                return new PreAuthenticatedAuthenticationToken(accessToken, token);
            }
            Token refreshToken = this.refreshTokenStringDeserializer.apply(token);
            if (refreshToken != null) {
                return new PreAuthenticatedAuthenticationToken(refreshToken, token);
            }
        }
        return null;
    }
}
