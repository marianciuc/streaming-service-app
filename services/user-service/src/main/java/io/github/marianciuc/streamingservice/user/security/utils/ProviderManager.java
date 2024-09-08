/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ProviderManager.java
 *
 */

package io.github.marianciuc.streamingservice.user.security.utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * Custom implementation of {@link AuthenticationManager} that iterates through a list of {@link AuthenticationProvider}s
 * to find a suitable provider for the given authentication request.
 */
public class ProviderManager  implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    public ProviderManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    /**
     * Attempts to authenticate the provided {@link Authentication} object by iterating through the list of
     * {@link AuthenticationProvider}s.
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }
        throw new AuthenticationException("No provider found for " + authentication.getClass().getName()) {};
    }
}
