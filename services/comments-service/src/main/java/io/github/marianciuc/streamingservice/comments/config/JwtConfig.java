/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JwtConfig.java
 *
 */

package io.github.marianciuc.streamingservice.comments.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;

@Slf4j
@Configuration
public class JwtConfig {

    @Bean
    public JWSVerifier jwsVerifier(WebClient webClient) throws ParseException, JOSEException {

        String publicKey = webClient.get()
                .uri("http://user-service/jwt/public")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assert publicKey != null;
        RSAKey publicJWK = RSAKey.parse(publicKey);

        return new RSASSAVerifier(publicJWK);
    }
}
