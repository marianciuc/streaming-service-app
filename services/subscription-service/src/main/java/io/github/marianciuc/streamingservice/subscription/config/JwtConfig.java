package io.github.marianciuc.streamingservice.subscription.config;

import io.github.marianciuc.jwtsecurity.service.JsonWebTokenFilter;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import io.github.marianciuc.jwtsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is a configuration class for JWT (JSON Web Token).
 * It includes the settings for access and refresh tokens and provides Beans for JWT services and filters.
 */
@Configuration
public class JwtConfig {

    /** Expiration time for an access token. */
    @Value("${security.jwt.token.accessExpiration}")
    private Long accessExpiration;

    /** Expiration time for a refresh token. */
    @Value("${security.jwt.token.refreshToken}")
    private Long refreshExpiration;

    /** Secret key for token generation and verification. */
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    /**
     * This bean provides a service for handling JWT.
     *
     * @return a new instance of JsonWebTokenService
     */
    @Bean
    public JsonWebTokenService jsonWebTokenService(){
        return new JsonWebTokenService(secretKey, accessExpiration, refreshExpiration);
    }

    /**
     * This bean provides a filter for JWT.
     *
     * @param jsonWebTokenService the JSON Web Token service
     * @return a new instance of JsonWebTokenFilter
     */
    @Bean
    public JsonWebTokenFilter jsonWebTokenFilter(JsonWebTokenService jsonWebTokenService){
        return new JsonWebTokenFilter(jsonWebTokenService);
    }

    /**
     * This bean provides a service for handling users.
     *
     * @return a new instance of UserService
     */
    @Bean
    public UserService userService(){
        return new UserService();
    }
}
