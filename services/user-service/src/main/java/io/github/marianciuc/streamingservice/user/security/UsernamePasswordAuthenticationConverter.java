/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UsernamePasswordAuthenticationConverter.java
 *
 */

package io.github.marianciuc.streamingservice.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marianciuc.streamingservice.user.dto.CredentialsRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class UsernamePasswordAuthenticationConverter implements AuthenticationConverter {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convert HttpServletRequest to UsernamePasswordAuthenticationToken
     * @param request - HttpServletRequest
     * @return UsernamePasswordAuthenticationToken authentication if credentials are valid, null otherwise
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        try {
            CredentialsRequest credentials = objectMapper.readValue(request.getInputStream(), CredentialsRequest.class);
            if (credentials != null &&
                    StringUtils.hasText(credentials.login()) &&
                    StringUtils.hasText(credentials.password())) {
                return new UsernamePasswordAuthenticationToken(credentials.login(), credentials.password());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
