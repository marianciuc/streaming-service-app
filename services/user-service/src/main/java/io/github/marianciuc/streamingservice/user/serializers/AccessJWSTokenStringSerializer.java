/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AccessJawsStringSerializer.java
 *
 */

package io.github.marianciuc.streamingservice.user.serializers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import io.github.marianciuc.streamingservice.user.exceptions.TokenSigningException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class AccessJWSTokenStringSerializer implements TokenSerializer {

    private final JWSSigner signer;
    private JWSAlgorithm algorithm = JWSAlgorithm.HS256;

    public AccessJWSTokenStringSerializer(JWSSigner signer) {
        this.signer = signer;
    }

    /**
     * Applies the token serialization process to produce a JWS-encoded representation of the given token.
     * @param token the token to be serialized;
     * @return a string representing the JWS-encoded token.
     * @throws TokenSigningException if the token signing process fails.
     */
    @Override
    public String apply(Token token) {
        var jwtHeader = new JWSHeader.Builder(algorithm)
                .keyID(token.tokenId().toString())
                .build();
        var jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.tokenId().toString())
                .claim("user-id", token.userId().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .issuer(token.issuer())
                .claim("authorities", token.roles().stream().map(s -> s.replace("GRAND::", "")).toList())
                .build();
        var signedJWT = new SignedJWT(jwtHeader, jwtClaimsSet);
        try {
            signedJWT.sign(this.signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT", e);
            throw new TokenSigningException("Failed to sign JWT", e);
        }
    }

    public void setAlgorithm(JWSAlgorithm algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithm cannot be null");
        }
        this.algorithm = algorithm;
    }

    public AccessJWSTokenStringSerializer algorithm(JWSAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }
}
