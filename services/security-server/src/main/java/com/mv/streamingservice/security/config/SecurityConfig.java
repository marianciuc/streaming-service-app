package com.mv.streamingservice.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * The {@code SecurityConfig} class is a configuration class that enables web security and provides methods to configure security filters.
 *
 * Note: The code examples are not included in the documentation for readability purposes.
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    /**
     * The defaultSecurityFilterCharin method creates a SecurityFilterChain object with default security configurations.
     *
     * @param http the HttpSecurity object used to configure the security filters
     * @return a SecurityFilterChain object with the default security configurations
     * @throws Exception if an error occurs while configuring the security filters
     */
    public SecurityFilterChain defaultSecurityFilterCharin(HttpSecurity http) throws Exception {
        return http.cors().disable().authorizeRequests().anyRequest().permitAll().and().csrf().disable().build();
    }
}
