/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JWTAuthenticationProvider.java
 *
 */

package io.github.marianciuc.streamingservice.user.security.providers;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.entity.JWTUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationUserDetailsService<Authentication> userDetailsService;

    public JWTAuthenticationProvider(AuthenticationUserDetailsService<Authentication> userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authenticating user with JWT token provided");
        Token token = (Token) authentication.getPrincipal();
        log.info("Token: {}", token);
        JWTUserPrincipal userPrincipal = (JWTUserPrincipal) userDetailsService.loadUserDetails(authentication);
        if (token != null) {
            return new PreAuthenticatedAuthenticationToken(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());
        }
        throw new BadCredentialsException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
