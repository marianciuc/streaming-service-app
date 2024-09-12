/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JWTAuthenticationConverter.java
 *
 */

package io.github.marianciuc.streamingservice.user.security.converters;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.serializers.TokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Converter to extract and process JWT tokens from HTTP requests.
 */
public class JWTAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer accessTokenStringDeserializer;
    private final TokenDeserializer refreshTokenStringDeserializer;

    /**
     * Constructs a JWTAuthenticationConverter with the given deserializers.
     *
     * @param accessTokenStringDeserializer the deserializer for access tokens.
     * @param refreshTokenStringDeserializer the deserializer for refresh tokens.
     */
    public JWTAuthenticationConverter(TokenDeserializer accessTokenStringDeserializer, TokenDeserializer refreshTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
    }

    /**
     * Converts the received HTTP request to an {@link Authentication} object based on JWT token.
     *
     * @param request the HttpServletRequest object.
     * @return an Authentication object or null if conversion fails.
     */
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
                System.out.println("refresh token found");
                return new PreAuthenticatedAuthenticationToken(refreshToken, token);
            }
        }
        return null;
    }
}
