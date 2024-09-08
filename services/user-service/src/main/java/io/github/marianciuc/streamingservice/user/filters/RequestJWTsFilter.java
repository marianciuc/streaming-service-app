/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RequestJWTsFilter.java
 *
 */

package io.github.marianciuc.streamingservice.user.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marianciuc.streamingservice.user.exceptions.AccessDeniedException;
import io.github.marianciuc.streamingservice.user.factories.AccessTokenFactory;
import io.github.marianciuc.streamingservice.user.factories.RefreshTokenFactory;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.dto.common.TokenPair;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.Function;


/**
 * Фильтр для обработки запроса на получение JWT токенов для авторизованного пользователей
 * При успешной аутентификации возвращает пару токенов: access и refresh
 *
 * @see RefreshTokenFactory
 * @see AccessTokenFactory
 * @see RequestAttributeSecurityContextRepository
 * @see Token
 * @see TokenPair
 */
@RequiredArgsConstructor
@Setter
public class RequestJWTsFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/tokens", HttpMethod.POST.name());
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private Function<Authentication, Token> refreshTokenFactory = new RefreshTokenFactory();
    private Function<Token, Token> accessTokenFactory = new AccessTokenFactory();
    private Function<Token, String> refreshTokenSerializer = Object::toString;
    private Function<Token, String> accessTokenSerializer = Object::toString;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            if (this.securityContextRepository.containsContext(request)) {

                DeferredSecurityContext deferredContext = this.securityContextRepository.loadDeferredContext(request);
                SecurityContext context = deferredContext.get();

                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken authenticationToken) {
                    var refreshToken = this.refreshTokenFactory.apply(authenticationToken);
                    var accessToken = this.accessTokenFactory.apply(refreshToken);

                    this.objectMapper.writeValue(response.getWriter(), new TokenPair(
                            this.accessTokenSerializer.apply(accessToken),
                            accessToken.expiresAt().toString(),
                            this.refreshTokenSerializer.apply(refreshToken),
                            refreshToken.expiresAt().toString()
                    ));
                    return;
                }
            }
            throw new AccessDeniedException("User must be authenticated to obtain tokens");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
