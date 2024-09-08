/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenAuthenticationUserDetailsService.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;

import io.github.marianciuc.streamingservice.user.dto.Token;
import io.github.marianciuc.streamingservice.user.entity.JWTUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<Authentication> {


    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
            if (authentication.getPrincipal() instanceof Token token) {
            return new JWTUserPrincipal(
                    token.username(),
                    "",
                    token.expiresAt().isBefore(Instant.now()),
                    true,
                    true,
                    true,
                    token.roles().stream().map(SimpleGrantedAuthority::new).toList(),
                    token,
                    token.userId()
            );
        }
        return null;
    }
}
