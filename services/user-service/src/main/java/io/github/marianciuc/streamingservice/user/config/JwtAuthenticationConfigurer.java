/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JwtAuthenticationConfigurer.java
 *
 */

package io.github.marianciuc.streamingservice.user.config;

import io.github.marianciuc.streamingservice.user.factories.AuthenticationTokenFactory;
import io.github.marianciuc.streamingservice.user.factories.TokenFactory;
import io.github.marianciuc.streamingservice.user.filters.JWTAuthenticationFilter;
import io.github.marianciuc.streamingservice.user.filters.UsernamePasswordAuthenticationFilter;
import io.github.marianciuc.streamingservice.user.security.*;
import io.github.marianciuc.streamingservice.user.serializers.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

public class JwtAuthenticationConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    private static final String API_TOKENS_PATH = "/api/v1/tokens";
    private static final String POST_METHOD = "POST";


    private TokenSerializer refreshTokenSerializer = Object::toString;

    private TokenSerializer accessTokenSerializer = Object::toString;

    private TokenDeserializer accessTokenStringDeserializer;
    private TokenDeserializer refreshTokenStringDeserializer;
    private AuthenticationTokenFactory refreshTokenFactory;

    private TokenFactory accessTokenFactory;
    private UserDetailsService credentialsUserDetailsService;
    private AuthenticationUserDetailsService<Authentication> jwtUserDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationProvider credentialsAuthenticationProvider;

    private AuthenticationProvider jwtAuthenticationProvider;
    private AuthenticationConverter jwtAuthenticationConverter;
    private AuthenticationConverter usernamePasswordAuthenticationConverter;
    @Override
    public void init(HttpSecurity builder) throws Exception {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher(API_TOKENS_PATH, POST_METHOD));
        }
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        initializeProvidersAndConverters();

        AuthenticationManager authManager = new ProviderManager(Arrays.asList(credentialsAuthenticationProvider, jwtAuthenticationProvider));

        var usernamePasswordAuthenticationFilter = createUsernamePasswordAuthenticationFilter(authManager);
        var jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtAuthenticationConverter, authManager);

        builder.addFilter(usernamePasswordAuthenticationFilter).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    private void initializeProvidersAndConverters() {
        credentialsAuthenticationProvider = new CredentialsAuthenticationProvider(credentialsUserDetailsService, passwordEncoder);
        jwtAuthenticationProvider = new JWTAuthenticationProvider(jwtUserDetailsService);
        jwtAuthenticationConverter = new JWTAuthenticationConverter(accessTokenStringDeserializer, refreshTokenStringDeserializer);
        usernamePasswordAuthenticationConverter = new UsernamePasswordAuthenticationConverter();
    }

    private UsernamePasswordAuthenticationFilter createUsernamePasswordAuthenticationFilter(AuthenticationManager authManager) {
        return new UsernamePasswordAuthenticationFilter(
                usernamePasswordAuthenticationConverter,
                accessTokenFactory,
                refreshTokenFactory,
                refreshTokenSerializer,
                accessTokenSerializer,
                authManager
        );
    }

    public JwtAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    public JwtAuthenticationConfigurer jwtUserDetailsService(AuthenticationUserDetailsService<Authentication> authenticationUserDetailsService) {
        this.jwtUserDetailsService = authenticationUserDetailsService;
        return this;
    }

    public JwtAuthenticationConfigurer credentialsUserDetailsService(UserDetailsService credentialsUserDetailsService) {
        this.credentialsUserDetailsService = credentialsUserDetailsService;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenFactory(TokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenFactory(AuthenticationTokenFactory refreshTokenFactory) {
        this.refreshTokenFactory = refreshTokenFactory;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringDeserializer(TokenDeserializer accessTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenStringDeserializer(TokenDeserializer refreshTokenStringDeserializer) {
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenSerializer(TokenSerializer accessTokenSerializer) {
        this.accessTokenSerializer = accessTokenSerializer;
        return this;
    }
    public JwtAuthenticationConfigurer refreshTokenSerializer(TokenSerializer refreshTokenSerializer) {
        this.refreshTokenSerializer = refreshTokenSerializer;
        return this;
    }
}
