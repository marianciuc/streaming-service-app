/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshTokenJweStringSerializer.java
 *
 */

package io.github.marianciuc.streamingservice.user.serializers;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.exceptions.TokenEncryptionException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Slf4j
public class RefreshJWETokenStringSerializer implements TokenSerializer {

    private final JWEEncrypter jweEncrypter;
    private static final JWEAlgorithm JWE_ALGORITHM = JWEAlgorithm.DIR;
    private static final EncryptionMethod JWE_ENCRYPTION_METHOD = EncryptionMethod.A128GCM;

    /**
     * Applies the token serialization process to produce a JWE-encoded representation of the given token.
     *
     * @param token the token to be serialized; contains claims and metadata for the JWE token.
     * @return a string representing the JWE-encoded token.
     * @throws TokenEncryptionException if the token encryption process fails.
     */
    @Override
    public String apply(Token token) {
        var jwtHeader = buildJweHeader(token);
        var jwtClaimsSet = buildJwtClaimsSet(token);
        return serializeToken(jwtHeader, jwtClaimsSet);
    }

    private JWEHeader buildJweHeader(Token token) {
        return new JWEHeader.Builder(JWE_ALGORITHM, JWE_ENCRYPTION_METHOD)
                .keyID(token.userId().toString())
                .build();
    }

    private JWTClaimsSet buildJwtClaimsSet(Token token) {
        return new JWTClaimsSet.Builder()
                .jwtID(token.userId().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .issuer(token.issuer())
                .claim("authorities", token.roles())
                .build();
    }

    private String serializeToken(JWEHeader jwtHeader, JWTClaimsSet jwtClaimsSet) {
        var encryptedJWT = new EncryptedJWT(jwtHeader, jwtClaimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error encrypting JWT", e);
            throw new TokenEncryptionException("Failed to encrypt JWT", e);
        }
    }
}
