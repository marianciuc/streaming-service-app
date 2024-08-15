package com.mv.streamingservice.security.config;

import com.mv.streamingservice.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The {@code ApplicationConfig} class represents the configuration for the application.
 * It provides various beans and configurations related to authentication and user details.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private AuthenticationService authenticationService;

    /**
     * Returns a PasswordEncoder object that uses the BCrypt algorithm for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns a UserDetailsService implementation that retrieves user details from the AuthenticationService by querying with a username.
     * @throws UsernameNotFoundException if the user is not found in the AuthenticationService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> authenticationService.findByQuery(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Retrieves the authentication provider used for user authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return createDaoAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    /**
     * Retrieves the authentication manager from the provided AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the configuration object containing the authentication manager
     * @return the authentication manager defined in the configuration
     * @throws Exception if an error occurs while retrieving the authentication manager
     */
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a new instance of DaoAuthenticationProvider with the specified UserDetailsService and PasswordEncoder.
     *
     * @param userDetailsService the UserDetailsService implementation used to retrieve user details for authentication
     * @param passwordEncoder the PasswordEncoder implementation used to encode and compare passwords
     * @return a new instance of DaoAuthenticationProvider configured with the provided UserDetailsService and PasswordEncoder
     */
    private DaoAuthenticationProvider createDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
