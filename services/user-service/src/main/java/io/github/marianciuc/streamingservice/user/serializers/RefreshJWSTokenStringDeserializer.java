/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefreshJWSTokenStringDeserializer.java
 *
 */

package io.github.marianciuc.streamingservice.user.serializers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import io.github.marianciuc.streamingservice.user.dto.common.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RefreshJWSTokenStringDeserializer implements TokenDeserializer {

    private final JWEDecrypter jweDecrypter;

    /**
     * Deserializes a token from a string representation
     *
     * @param s String representation of the token to be deserialized
     * @return Token object or null if the token is invalid
     */
    @Override
    public Token apply(String s) {
        try {
            var signedJWT = EncryptedJWT.parse(s);
            signedJWT.decrypt(this.jweDecrypter);
            return new Token(
                    UUID.fromString(signedJWT.getHeader().getKeyID()),
                    UUID.fromString(signedJWT.getJWTClaimsSet().getClaim("user-id").toString()),
                    signedJWT.getJWTClaimsSet().getSubject(),
                    signedJWT.getJWTClaimsSet().getIssuer(),
                    signedJWT.getJWTClaimsSet().getStringListClaim("authorities"),
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant()
            );
        } catch (ParseException | JOSEException | IllegalArgumentException e) {
            log.error("Error parsing JWT", e);
            return null;
        }
    }
}
