/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SecurityUtils.java
 *
 */

package io.github.marianciuc.streamingservice.user.security.utils;

import io.github.marianciuc.streamingservice.user.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.user.exceptions.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Utility class for handling security-related operations.
 */
public class SecurityUtils {


    /**
     * Extracts the JWTUserPrincipal object from the current security context
     * @return JWTUserPrincipal object
     * @see JWTUserPrincipal
     * @throws AccessDeniedException if the user is not authenticated or the principal is not an instance of JWTUserPrincipal or the authentication is not an instance of PreAuthenticatedAuthenticationToken
     */
    public static JWTUserPrincipal extractJwtUserPrincipals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof PreAuthenticatedAuthenticationToken && authentication.getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            return jwtUserPrincipal;
        }
        else throw new AccessDeniedException("Access denied: User is not authenticated or has invalid principal.");
    }
}
