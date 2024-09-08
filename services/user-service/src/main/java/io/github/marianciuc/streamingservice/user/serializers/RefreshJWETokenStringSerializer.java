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
    private JWEAlgorithm JWE_ALGORITHM = JWEAlgorithm.DIR;
    private EncryptionMethod JWE_ENCRYPTION_METHOD = EncryptionMethod.A128GCM;

    /**
     * Applies the token serialization process to produce a JWE-encoded representation of the given token.
     *
     * @param token the token to be serialized; contains claims and metadata for the JWE token.
     * @return a string representing the JWE-encoded token.
     * @throws TokenEncryptionException if the token encryption process fails.
     */
    @Override
    public String apply(Token token) {
        var jwtHeader =  new JWEHeader.Builder(JWE_ALGORITHM, JWE_ENCRYPTION_METHOD)
                .keyID(token.userId().toString())
                .build();

        var jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.userId().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .claim("user-id", token.userId().toString())
                .expirationTime(Date.from(token.expiresAt()))
                .issuer(token.issuer())
                .claim("authorities", token.roles())
                .build();
        log.info("JWT Claims Set: {}", jwtClaimsSet);
        var encryptedJWT = new EncryptedJWT(jwtHeader, jwtClaimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error encrypting JWT", e);
            throw new TokenEncryptionException("Failed to encrypt JWT", e);
        }
    }

    public void setJweAlgorithm(JWEAlgorithm jweAlgorithm) {
        this.JWE_ALGORITHM = jweAlgorithm;
    }

    public void setJweEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.JWE_ENCRYPTION_METHOD = encryptionMethod;
    }
}
