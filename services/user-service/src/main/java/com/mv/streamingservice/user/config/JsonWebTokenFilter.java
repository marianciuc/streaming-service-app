package com.mv.streamingservice.user.config;

import com.mv.streamingservice.user.entity.User;
import com.mv.streamingservice.user.enums.Role;
import com.mv.streamingservice.user.services.JsonWebTokenService;
import com.mv.streamingservice.user.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jsonWebTokenService;
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        final String userAuthHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String serviceAuthHeader = request.getHeader(HttpHeaders.WWW_AUTHENTICATE);

        String jwt;
        Role role;

        if (userAuthHeader != null && userAuthHeader.startsWith("Bearer ")) {
            jwt = userAuthHeader.substring(7);
            UUID userId = jsonWebTokenService.extractUserId(jwt);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getById(userId);
                setUserAuthentication(user, request);
            }
        } else if (serviceAuthHeader != null && serviceAuthHeader.startsWith("Bearer ")) {
            jwt = serviceAuthHeader.substring(7);
            role = jsonWebTokenService.extractRole(jwt);
            if (Role.ROLE_SERVICE.equals(role)) {
                User user = User.builder()
                        .username(jwt)
                        .role(role)
                        .build();
                setUserAuthentication(user, request);
            }
        }
        chain.doFilter(request, response);
    }

    private void setUserAuthentication(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
