package io.github.marianciuc.streamingservice.media.config;

import io.github.marianciuc.jwtsecurity.filters.JsonWebTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class provides the configuration for the security of the application.
 * It enables web security and defines the security filter chain for handling HTTP requests.
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JsonWebTokenFilter jsonWebTokenService;

    /**
     * This method defines the security filter chain for handling HTTP requests.
     * It disables CSRF protection, configures authorization for all requests,
     * sets the session creation policy as stateless, and adds a custom filter before the
     * UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity object used for configuring security
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers("/api/v1/media/images/{picture-id}").permitAll())
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jsonWebTokenService, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
