package com.mv.streamingservice.user.config;

import com.mv.streamingservice.user.services.UserService;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final static String BEARER_STR = "Bearer ";
    private final JsonWebTokenService jsonWebTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (authHeader != null && authHeader.startsWith(BEARER_STR)) {
            String jwt = authHeader.substring(BEARER_STR.length());
            UserDetails userDetails = jsonWebTokenService.parseAccessToken(jwt);
            setUserAuthentication(userDetails, request);
        }

        chain.doFilter(request, response);
    }

    private void setUserAuthentication(UserDetails user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
