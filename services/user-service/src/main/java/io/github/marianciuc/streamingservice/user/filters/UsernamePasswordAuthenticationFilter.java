/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UsernamePasswordAuthenticationFilter.java
 *
 */

package io.github.marianciuc.streamingservice.user.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.dto.common.TokenPair;
import io.github.marianciuc.streamingservice.user.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.user.entity.UserPrincipal;
import io.github.marianciuc.streamingservice.user.factories.AuthenticationTokenFactory;
import io.github.marianciuc.streamingservice.user.factories.TokenFactory;
import io.github.marianciuc.streamingservice.user.serializers.TokenSerializer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * Filter for processing username and password authentication requests.
 */
@Setter
@Slf4j
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationConverter authenticationConverter;
    private final TokenFactory accessTokenFactory;
    private final AuthenticationTokenFactory refreshTokenFactory;
    private final TokenSerializer accessTokenSerializer;
    private final TokenSerializer refreshTokenSerializer;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructs a UsernamePasswordAuthenticationFilter with the provided parameters.
     *
     * @param authenticationConverter the converter to extract authentication from the request.
     * @param accessTokenFactory      the factory to create access tokens.
     * @param refreshTokenFactory     the factory to create refresh tokens.
     * @param accessTokenSerializer   the serializer for access tokens.
     * @param refreshTokenSerializer  the serializer for refresh tokens.
     * @param authenticationManager   the manager to handle the authentication process.
     */
    public UsernamePasswordAuthenticationFilter(AuthenticationConverter authenticationConverter,
                                                TokenFactory accessTokenFactory,
                                                AuthenticationTokenFactory refreshTokenFactory,
                                                TokenSerializer accessTokenSerializer,
                                                TokenSerializer refreshTokenSerializer,
                                                AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
        this.authenticationConverter = authenticationConverter;
        this.accessTokenFactory = accessTokenFactory;
        this.refreshTokenFactory = refreshTokenFactory;
        this.accessTokenSerializer = accessTokenSerializer;
        this.refreshTokenSerializer = refreshTokenSerializer;
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Attempts to authenticate the user by extracting credentials from the request.
     *
     * @param request  the HTTP request object.
     * @param response the HTTP response object.
     * @return the Authentication object if successful.
     * @throws AuthenticationException if authentication attempt fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Authentication authRequest = authenticationConverter.convert(request);
        if (authRequest == null) {
            throw new AuthenticationException("Failed to convert authentication request") {};
        }
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Handles successful authentication by creating and returning token pairs.
     *
     * @param request    the HTTP request object.
     * @param response   the HTTP response object.
     * @param chain      the filter chain.
     * @param authResult the result of the authentication process.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        Token refreshToken = refreshTokenFactory.apply(authResult);
        Token accessToken = accessTokenFactory.apply(refreshToken);

        String refreshTokenString = refreshTokenSerializer.apply(refreshToken);
        String accessTokenString = accessTokenSerializer.apply(refreshToken);

        TokenPair tokenPair = new TokenPair(accessTokenString, accessToken.expiresAt().toString(),
                refreshTokenString, refreshToken.expiresAt().toString());
        String jsonTokenPair = objectMapper.writeValueAsString(tokenPair);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getWriter().write(jsonTokenPair);
    }
}
