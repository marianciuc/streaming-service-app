package com.mv.streamingservice.security.services;

import com.mv.streamingservice.security.exceptions.JsonWebTokenExpiredException;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpringBootTest
public class JsonWebTokenServiceTest {
    @Test
    public void testExtractUsername_NullJwt_ThrowsException() {
        JsonWebTokenService jwtService = new JsonWebTokenService();
        Assertions.assertThrows(RuntimeException.class, () -> jwtService.extractUsername(null));
    }

    @Test
    public void testExtractUsername_InvalidJwt_ThrowsException() {
        JsonWebTokenService jwtService = new JsonWebTokenService();
        Assertions.assertThrows(RuntimeException.class, () -> jwtService.extractUsername("invalid.jwt"));
    }

    @Test
    public void testExtractUsername_ValidJwtWithUsername_ReturnsCorrectUsername() {
        final String testUsername = "TestUser";
        JsonWebTokenService jwtService = new JsonWebTokenService();

        UserDetails userDetails = User.withDefaultPasswordEncoder()
            .username(testUsername)
            .password("password")
            .roles("USER")
            .build();

        String jwt;
        try {
            jwt = jwtService.generateToken(userDetails);
        } catch (JOSEException e) {
            Assertions.fail("JOSE Exception thrown: " + e.getMessage());
            return;
        }

        Assertions.assertEquals(testUsername, jwtService.extractUsername(jwt));
    }

}
