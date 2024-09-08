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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Slf4j
public class AccessJWSTokenStringSerializer implements TokenSerializer {

    private final JWSSigner signer;
    private static final JWSAlgorithm ALGORITHM = JWSAlgorithm.HS256;

    /**
     * Applies the token serialization process to produce a JWS-encoded representation of the given token.
     * @param token the token to be serialized;
     * @return a string representing the JWS-encoded token.
     * @throws TokenSigningException if the token signing process fails.
     */
    @Override
    public String apply(@NotNull @Valid Token token) {
        var jwtHeader = buildJwtHeader(token);
        var jwtClaimsSet = buildJwtClaimsSet(token);
        return signAndSerializeJwt(jwtHeader, jwtClaimsSet);
    }

    private JWSHeader buildJwtHeader(Token token) {
        return new JWSHeader.Builder(ALGORITHM)
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

    private String signAndSerializeJwt(JWSHeader jwtHeader, JWTClaimsSet jwtClaimsSet) {
        var signedJWT = new SignedJWT(jwtHeader, jwtClaimsSet);
        try {
            signedJWT.sign(this.signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT", e);
            throw new TokenSigningException("Failed to sign JWT", e);
        }
    }
}
