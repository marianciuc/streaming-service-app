package com.mv.streamingservice.security.config;

import com.mv.streamingservice.security.services.JsonWebTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The JsonWebTokenFilter class is a Spring Web Filter that handles JSON Web Token (JWT) authentication.
 * It extracts the JWT from the "Authorization" header, validates it, and authenticates the user.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jsonWebTokenService;
    private final UserDetailsService userDetailsService;

    /**
     * This method is responsible for processing the HTTP request and performing JSON Web Token (JWT) authentication.
     * It extracts the JWT from the "Authorization" header, validates it, and authenticates the user.
     *
     * @param request      the HttpServletRequest object representing the current request
     * @param response     the HttpServletResponse object representing the current response
     * @param filterChain  the FilterChain object for invoking the next filter in the chain
     * @throws ServletException if the request encountered an exception that prevented it from being processed
     * @throws IOException      if an I/O error occurred while processing the request or response
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("JsonWebTokenFilter");

        final String authHeader = request.getHeader("Authorization");
        final String jsonWebToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("No Token");

            filterChain.doFilter(request, response);
            return;
        }

        jsonWebToken = authHeader.substring(7);
        username = this.jsonWebTokenService.extractUsername(jsonWebToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jsonWebTokenService.isTokenValid(jsonWebToken, userDetails)) {
                this.authenticateUser(request, userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Authenticates the user based on the supplied UserDetails and sets the authentication
     * token in the SecurityContextHolder.
     *
     * @param request     the HttpServletRequest object representing the current request
     * @param userDetails the UserDetails object representing the user details
     */
    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
