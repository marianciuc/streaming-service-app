/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AccessJWSTokenStringSerializerTest.java
 *
 */

package io.github.marianciuc.streamingservice.user.serializers;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.exceptions.TokenSigningException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class AccessJWSTokenStringSerializerTest {

    private JWSSigner signer;
    private AccessJWSTokenStringSerializer serializer;

    @BeforeEach
    void setUp() {
        String secret = "thisismysercetthisismysercetthisismysercetthisismysercet";
        try {
            signer = new MACSigner(secret);
        } catch (JOSEException e) {
            fail("Failed to create MACSigner", e);
        }
        serializer = new AccessJWSTokenStringSerializer(signer);
    }

    @Test
    public void testApplyToken_correctSigning() {
        UUID uuid = UUID.randomUUID();
        Token token = new Token(uuid, uuid, "TestSubject",
                "TestIssuer", Collections.singletonList("TestRole"), Instant.now(), Instant.now());

        String jwtString = serializer.apply(token);

        assertNotNull(jwtString);
        log.info("JWT String: {}", jwtString);
    }
}