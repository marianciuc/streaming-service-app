/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import io.github.marianciuc.streamingservice.payment.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.payment.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UUID extractUserIdFromAuth() {
        JWTUserPrincipal jwtUserPrincipal = this.extractUserFromAuth();
        return jwtUserPrincipal.getUserId();
    }

    @Override
    public boolean hasAdminRoles() {
        JWTUserPrincipal jwtUserPrincipal = this.extractUserFromAuth();
        return jwtUserPrincipal.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    private JWTUserPrincipal extractUserFromAuth() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            return jwtUserPrincipal;
        } else throw new AccessDeniedException("Access denied");
    }
}
