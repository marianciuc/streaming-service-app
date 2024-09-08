/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UsernamePasswordAuthenticationFilter.java
 *
 */

package io.github.marianciuc.streamingservice.user.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marianciuc.streamingservice.user.dto.Token;
import io.github.marianciuc.streamingservice.user.dto.TokenPair;
import io.github.marianciuc.streamingservice.user.factories.AuthenticationTokenFactory;
import io.github.marianciuc.streamingservice.user.factories.TokenFactory;
import io.github.marianciuc.streamingservice.user.serializers.TokenSerializer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.apache.hc.core5.http.ContentType;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Setter
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationConverter authenticationConverter;
    private final TokenFactory accessTokenFactory;
    private final AuthenticationTokenFactory refreshTokenFactory;
    private final TokenSerializer accessTokenSerializer;
    private final TokenSerializer refreshTokenSerializer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public UsernamePasswordAuthenticationFilter(AuthenticationConverter authenticationConverter, TokenFactory accessTokenFactory, AuthenticationTokenFactory refreshTokenFactory, TokenSerializer accessTokenSerializer, TokenSerializer refreshTokenSerializer, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
        this.authenticationConverter = authenticationConverter;
        this.accessTokenFactory = accessTokenFactory;
        this.refreshTokenFactory = refreshTokenFactory;
        this.accessTokenSerializer = accessTokenSerializer;
        this.refreshTokenSerializer = refreshTokenSerializer;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return authenticationConverter.convert(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Token refreshToken = refreshTokenFactory.apply(authResult);
        Token accessToken = accessTokenFactory.apply(refreshToken);

        String refreshTokenString = refreshTokenSerializer.apply(refreshToken);
        String accessTokenString = accessTokenSerializer.apply(accessTokenFactory.apply(refreshToken));

        TokenPair tokenPair = new TokenPair(accessTokenString, accessToken.expiresAt().toString(), refreshTokenString, refreshToken.expiresAt().toString());
        String jsonTokenPair = objectMapper.writeValueAsString(tokenPair);
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getWriter().write(jsonTokenPair);
    }

}
