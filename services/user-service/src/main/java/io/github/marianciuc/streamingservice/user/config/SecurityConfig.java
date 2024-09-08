package io.github.marianciuc.streamingservice.user.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
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
import org.springframework.web.cors.CorsConfigurationSource;

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
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
            @Value("${jwt.access-token-key}") String accessTokenKey,
            @Value("${jwt.refresh-token-key}") String refreshTokenKey,
            TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService,
            UserService userDetailsService,
            PasswordEncoder passwordEncoder
    ) throws ParseException, JOSEException {

        TokenDeserializer accessTokenDeserializer = new AccessJWETokenStringDeserializer(new MACVerifier(OctetSequenceKey.parse(accessTokenKey)));
        TokenDeserializer refreshTokenDeserializer = new RefreshJWSTokenStringDeserializer(new DirectDecrypter(OctetSequenceKey.parse(refreshTokenKey)));
        TokenSerializer accessTokenSerializer = new AccessJWSTokenStringSerializer(new MACSigner(OctetSequenceKey.parse(accessTokenKey)));
        TokenSerializer refreshTokenSerializer = new RefreshJWETokenStringSerializer(new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey)));

        return new JwtAuthenticationConfigurer()
                .passwordEncoder(passwordEncoder)
                .jwtUserDetailsService(tokenAuthenticationUserDetailsService)
                .credentialsUserDetailsService(userDetailsService)
                .accessTokenFactory(new AccessTokenFactory())
                .refreshTokenFactory(new RefreshTokenFactory())
                .accessTokenStringDeserializer(accessTokenDeserializer)
                .refreshTokenStringDeserializer(refreshTokenDeserializer)
                .accessTokenSerializer(accessTokenSerializer)
                .refreshTokenSerializer(refreshTokenSerializer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
