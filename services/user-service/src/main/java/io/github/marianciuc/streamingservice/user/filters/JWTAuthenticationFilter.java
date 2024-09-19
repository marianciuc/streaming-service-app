/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JsonWebTokeb.java
 *
 */

package io.github.marianciuc.streamingservice.user.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import java.io.IOException;

/**
 * Filter that processes JWT authentication from the HTTP Authorization header.
 */
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationConverter authenticationConverter;

    /**
     * Constructs a JWTAuthenticationFilter with the provided {@link AuthenticationConverter} and {@link AuthenticationManager}.
     *
     * @param authenticationConverter the converter to extract authentication from the request.
     * @param authenticationManager   the manager to handle the authentication process.
     */
    public JWTAuthenticationFilter(AuthenticationConverter authenticationConverter, AuthenticationManager authenticationManager) {
        super(new RequestHeaderRequestMatcher(HttpHeaders.AUTHORIZATION));
        this.authenticationConverter = authenticationConverter;
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Attempts to authenticate the request by extracting JWT token.
     *
     * @param request  the HTTP request object.
     * @param response the HTTP response object.
     * @return the Authentication object if successful, null otherwise.
     * @throws AuthenticationException, IOException, ServletException if authentication attempt fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Authentication authRequest = authenticationConverter.convert(request);
        if (authRequest == null) {
            throw new AuthenticationException("Failed to convert authentication request") {};
        }
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Handles successful authentication by continuing the filter chain.
     *
     * @param request    the HTTP request object.
     * @param response   the HTTP response object.
     * @param chain      the filter chain.
     * @param authResult the result of the authentication process.
     * @throws IOException, ServletException if an I/O error occurs.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
