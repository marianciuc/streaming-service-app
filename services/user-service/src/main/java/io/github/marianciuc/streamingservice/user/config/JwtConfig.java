package io.github.marianciuc.streamingservice.user.config;

import io.github.marianciuc.jwtsecurity.filters.JsonWebTokenFilter;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.jwtsecurity.service.impl.JsonWebTokenServiceImpl;
import io.github.marianciuc.jwtsecurity.service.impl.UserServiceImpl;
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

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    JsonWebTokenService jsonWebTokenService(){
        return new JsonWebTokenServiceImpl(serviceName, secretKey, accessExpiration, refreshExpiration);
    }

    @Bean
    UserService userService (){
        return new UserServiceImpl();
    }
    @Bean
    JsonWebTokenFilter jsonWebTokenFilter(){
        return new JsonWebTokenFilter(jsonWebTokenService(), userService());
    }
}
