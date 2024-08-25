package io.github.marianciuc.streamingservice.user.config;

import io.github.marianciuc.jwtsecurity.service.JsonWebTokenFilter;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${security.jwt.token.accessExpiration}")
    private Long accessExpiration;

    @Value("${security.jwt.token.refreshToken}")
    private Long refreshExpiration;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;


    @Bean
    JsonWebTokenService jsonWebTokenService(){
        return new JsonWebTokenService(secretKey, accessExpiration, refreshExpiration);
    }
}
