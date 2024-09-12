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
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class RefreshJWETokenStringSerializer implements TokenSerializer {

    private final JWEEncrypter jweEncrypter;
    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    /**
     * Applies the token serialization process to produce a JWE-encoded representation of the given token.
     *
     * @param token the token to be serialized; contains claims and metadata for the JWE token.
     * @return a string representing the JWE-encoded token.
     * @throws TokenEncryptionException if the token encryption process fails.
     */
    @Override
    public String apply(Token token) {
        var jwtHeader =  new JWEHeader.Builder(jweAlgorithm, encryptionMethod)
                .keyID(token.userId().toString())
                .contentType("JWT")
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

        JWEObject jweObject = new JWEObject(jwtHeader, new Payload(jwtClaimsSet.toJSONObject()));
        try {
            jweObject.encrypt(this.jweEncrypter);
            return jweObject.serialize();
        } catch (JOSEException e) {
            log.error("Error encrypting JWT", e);
            throw new TokenEncryptionException("Failed to encrypt JWT", e);
        }
    }

    public RefreshJWETokenStringSerializer encryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
        return this;
    }

    public RefreshJWETokenStringSerializer jweAlgorithm(JWEAlgorithm jweAlgorithm) {
        this.jweAlgorithm = jweAlgorithm;
        return this;
    }

}
