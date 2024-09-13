package io.github.marianciuc.streamingservice.user.config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import io.github.marianciuc.streamingservice.user.factories.AccessTokenFactory;
import io.github.marianciuc.streamingservice.user.factories.RefreshTokenFactory;
import io.github.marianciuc.streamingservice.user.serializers.*;
import io.github.marianciuc.streamingservice.user.services.UserService;
import io.github.marianciuc.streamingservice.user.services.impl.TokenAuthenticationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.text.ParseException;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConfigurer jwtAuthenticationConfigurer) throws Exception {
        http.apply(jwtAuthenticationConfigurer);
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
            @Value("${jwt.private-key}") String privateKey,
            @Value("${jwt.public-key}") String publicKey,
            TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService,
            UserService userDetailsService,
            PasswordEncoder passwordEncoder
    ) throws ParseException, JOSEException {

        RSAKey privateJWK = RSAKey.parse(privateKey);
        RSAKey publicJWK = RSAKey.parse(publicKey);

        JWSSigner signer = new RSASSASigner(privateJWK);
        JWEEncrypter encrypter = new RSAEncrypter(publicJWK);
        JWEDecrypter decrypter = new RSADecrypter(privateJWK);
        JWSVerifier verifier = new RSASSAVerifier(publicJWK);

        TokenDeserializer accessTokenDeserializer = new AccessJWETokenStringDeserializer(verifier);
        TokenSerializer accessTokenSerializer = new AccessJWSTokenStringSerializer(signer).algorithm(JWSAlgorithm.RS256);
        TokenDeserializer refreshTokenDeserializer = new RefreshJWSTokenStringDeserializer(decrypter);
        TokenSerializer refreshTokenSerializer =
                new RefreshJWETokenStringSerializer(encrypter)
                        .jweAlgorithm(JWEAlgorithm.RSA_OAEP_256)
                        .encryptionMethod(EncryptionMethod.A256GCM);

        return new JwtAuthenticationConfigurer()
                .passwordEncoder(passwordEncoder)
                .jwtUserDetailsService(tokenAuthenticationUserDetailsService)
                .credentialsUserDetailsService(userDetailsService)
                .accessTokenFactory(new AccessTokenFactory())
                .refreshTokenFactory(new RefreshTokenFactory())
                .accessTokenStringDeserializer(accessTokenDeserializer)
                .refreshTokenStringDeserializer(refreshTokenDeserializer)
                .accessTokenSerializer(accessTokenSerializer)
                .publicKey(publicKey)
                .refreshTokenSerializer(refreshTokenSerializer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
