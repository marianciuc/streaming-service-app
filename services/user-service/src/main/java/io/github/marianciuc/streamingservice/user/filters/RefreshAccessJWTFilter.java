/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshAccessJWTFilter.java
 *
 */

package io.github.marianciuc.streamingservice.user.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.dto.common.TokenPair;
import io.github.marianciuc.streamingservice.user.factories.RefreshTokenFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.function.Function;

@Setter
public class RefreshAccessJWTFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/refresh", HttpMethod.POST.name());
    private SecurityContextRepository securityContextRepository;

    private Function<Token, Token> accessTokenFactory;
    private Function<Authentication, Token> refreshTokenFactory = new RefreshTokenFactory();
    private Function<Token, String> accessTokenSerializer = Object::toString;
    private Function<Token, String> refreshTokenSerializer = Object::toString;
    private Boolean includeRefreshToken = false;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            if (securityContextRepository.containsContext(request)) {
                SecurityContext context = securityContextRepository.loadDeferredContext(request).get();
                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken && context.getAuthentication().getPrincipal() instanceof Token token) {
                    var accessToken = accessTokenFactory.apply(token);
                    if (accessToken != null) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                        if (includeRefreshToken) {
                            var refreshToken = refreshTokenFactory.apply(context.getAuthentication());
                            this.objectMapper.writeValue(response.getWriter(),
                                    new TokenPair(accessTokenSerializer.apply(accessToken), accessToken.expiresAt().toString(), refreshTokenSerializer.apply(refreshToken), refreshToken.expiresAt().toString()));
                            return;
                        }
                        this.objectMapper.writeValue(response.getWriter(),
                                new TokenPair(accessTokenSerializer.apply(accessToken), accessToken.expiresAt().toString(), null, null));
                        return;
                    }
                }
                throw new AccessDeniedException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
