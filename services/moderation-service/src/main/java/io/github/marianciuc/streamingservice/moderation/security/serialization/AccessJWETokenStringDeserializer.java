/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AccessJWETokenStringDeserializer.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.security.serialization;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.github.marianciuc.streamingservice.moderation.security.dto.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class AccessJWETokenStringDeserializer implements TokenDeserializer {

    private final JWSVerifier verifier;

    /**
     * Deserializes a token from a string representation
     *
     * @param token the token to be deserialized
     * @return Token object or null if the token is invalid
     */
    @Override
    public Token apply(String token) {
        try {
            var encryptedJWT = SignedJWT.parse(token);
            if (encryptedJWT.verify(this.verifier)) {
                return new Token(
                        UUID.fromString(encryptedJWT.getHeader().getKeyID()),
                        UUID.fromString(encryptedJWT.getJWTClaimsSet().getClaim("user-id").toString()),
                        encryptedJWT.getJWTClaimsSet().getSubject(),
                        encryptedJWT.getJWTClaimsSet().getIssuer(),
                        encryptedJWT.getJWTClaimsSet().getStringListClaim("authorities"),
                        encryptedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                        encryptedJWT.getJWTClaimsSet().getExpirationTime().toInstant()
                );
            }
            return null;
        } catch (ParseException | JOSEException | IllegalArgumentException e) {
            log.error("Error parsing JWT", e);
            return null;
        }
    }
}
