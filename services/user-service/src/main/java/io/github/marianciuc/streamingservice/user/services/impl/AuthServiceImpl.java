/*
 *  Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 *  Project: STREAMING SERVICE APP
 *  File: AuthServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;

import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import io.github.marianciuc.jwtsecurity.service.JwtUserDetails;
import io.github.marianciuc.streamingservice.user.dto.CredentialsRequest;
import io.github.marianciuc.streamingservice.user.dto.JsonWebTokenResponse;
import io.github.marianciuc.streamingservice.user.dto.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.exceptions.AccessDeniedException;
import io.github.marianciuc.streamingservice.user.exceptions.SecurityBadCredentialsException;
import io.github.marianciuc.streamingservice.user.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String BAD_CREDENTIALS_EXCEPTION = "Bad credentials";
    private static final String ACCESS_DENIED_EXCEPTION = "User does not have the necessary permissions.";
    private static final String DEFAULT_VALUE = "";

    private final UserServiceImpl userService;
    private final JsonWebTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public JsonWebTokenResponse authenticateUserByCredentials(CredentialsRequest request) {
        try {
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(request.login(), request.password());
            authenticationManager.authenticate(userToken);

            return getJWTResponseFromAuthenticationContext();
        } catch (AuthenticationException e) {
            throw new SecurityBadCredentialsException(BAD_CREDENTIALS_EXCEPTION);
        }
    }

    @Override
    public JsonWebTokenResponse refreshToken(String refreshToken) {
        JwtUserDetails jwtUserDetails = jwtService.parseRefreshToken(refreshToken);
        User user = (User) userDetailsService.loadUserByUsername(jwtUserDetails.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        return getJWTResponseFromAuthenticationContext();
    }

    @Override
    public JsonWebTokenResponse registerUser(RegistrationRequest request, Authentication authentication) {
        if (authentication != null) {
            User userRegistrationProvider = (User) authentication.getPrincipal();
            if (userRegistrationProvider.getRole().equals(Role.ROLE_ADMIN)) {
                userService.createUser(request, request.role());
                return new JsonWebTokenResponse(DEFAULT_VALUE, DEFAULT_VALUE);
            } else throw new AccessDeniedException(ACCESS_DENIED_EXCEPTION);
        } else {
            User user = userService.createUser(request, Role.ROLE_UNSUBSCRIBED_USER);
            return getJWTResponseFromUser(user);
        }
    }

    private JsonWebTokenResponse getJWTResponseFromAuthenticationContext() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getJWTResponseFromUser(user);
    }

    private JsonWebTokenResponse getJWTResponseFromUser(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new JsonWebTokenResponse(accessToken, refreshToken);
    }
}
