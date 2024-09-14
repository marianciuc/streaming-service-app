/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JWTFilter.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.security.filters;

import io.github.marianciuc.streamingservice.moderation.security.dto.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.moderation.security.dto.Token;
import io.github.marianciuc.streamingservice.moderation.security.serialization.TokenDeserializer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final io.github.marianciuc.streamingservice.moderation.security.serialization.TokenDeserializer tokenDeserializer;

    public JWTFilter(TokenDeserializer tokenDeserializer) {
        this.tokenDeserializer = tokenDeserializer;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Token jwtToken = tokenDeserializer.apply(token);

            if (jwtToken != null) {
                JWTUserPrincipal jwtUserPrincipal = new JWTUserPrincipal(jwtToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUserPrincipal, "", jwtUserPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User authenticated: {}", jwtUserPrincipal);
                log.info("Security context authentication: {}", SecurityContextHolder.getContext().getAuthentication());
            } else {
                log.warn("Failed to deserialize JWT token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
