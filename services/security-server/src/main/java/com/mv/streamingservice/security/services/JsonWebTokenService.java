package com.mv.streamingservice.security.services;

import com.mv.streamingservice.security.exceptions.JsonWebTokenExpiredException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;

/**
 * The JsonWebTokenService class handles JSON Web Token (JWT) generation, extraction, validation, and decryption operations.
 */
@RequiredArgsConstructor
@Service
@ConfigurationProperties(prefix = "jwt")
public class JsonWebTokenService {

    private static final String SECRET_KEY_ALG = "AES";

    /**
     * The secretKey variable holds the secret key used for JSON Web Token (JWT) encryption and decryption.
     * This variable is used in the JsonWebTokenService class for encrypting and decrypting JWTs.
     */
    private String secretKey;
    private Long expiration;


    /**
     * Extracts the username from the given JSON web token.
     *
     * @param jsonWebToken the JSON web token from which to extract the username
     * @return the extracted username
     */
    public String extractUsername(String jsonWebToken) {
        return this.extractClaim(jsonWebToken, JWTClaimsSet::getSubject);
    }

    /**
     * Checks if the given JSON Web Token (JWT) is valid.
     *
     * @param jsonWebToken The JSON Web Token to check for validity.
     * @param userDetails  The UserDetails object containing the user details.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String jsonWebToken, UserDetails userDetails) {
        final String username = this.extractUsername(jsonWebToken);
        return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(jsonWebToken);
    }

    /**
     * Checks if the given JSON Web Token (JWT) has expired.
     *
     * @param jsonWebToken The JSON Web Token to check for expiration.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String jsonWebToken) {
        return this.extractExpiration(jsonWebToken).before(new Date());
    }

    /**
     * Extracts the expiration date from a JSON Web Token (JWT).
     *
     * @param jsonWebToken The JSON Web Token from which to extract the expiration date.
     * @return The expiration date of the JSON Web Token.
     */
    private Date extractExpiration(String jsonWebToken) {
        return this.extractClaim(jsonWebToken, JWTClaimsSet::getExpirationTime);
    }

    /**
     * Extracts a specific claim from the given JSON Web Token (JWT).
     *
     * @param <T>            The type of the claim value to extract.
     * @param token          The JSON Web Token from which to extract the claim.
     * @param claimsResolver A Function that maps the Claims object to the desired claim value.
     * @return The extracted claim value.
     */
    public <T> T extractClaim(String jsonWebToken, Function<JWTClaimsSet, T> claimsResolver) {
        final JWTClaimsSet claims = extractAllClaims(jsonWebToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JSON Web Token (JWT).
     *
     * @param token The JSON Web Token from which to extract the claims.
     * @return The Claims object containing all the claims from the JSON Web Token.
     * @throws JsonWebTokenExpiredException If the token has expired.
     */
    private JWTClaimsSet extractAllClaims(String jsonWebToken) {
        try {
            return extractSignedJWT(jsonWebToken).getJWTClaimsSet();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the signed JSON Web Token (JWT) from the given input string.
     *
     * @param jsonWebToken the JSON Web Token to extract
     * @return the extracted SignedJWT object
     *
     * @throws RuntimeException if an error occurs during parsing, decryption, verification, or if any of the underlying
     *                          libraries throw a JOSEException or ParseException
     */
    private SignedJWT extractSignedJWT(String jsonWebToken) {
        try {
            JWEObject jweObject = JWEObject.parse(jsonWebToken);
            jweObject.decrypt(new DirectDecrypter(this.getSecretKey().getEncoded()));
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
            this.verifySignedJWT(signedJWT);
            return signedJWT;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifies the signature of a given SignedJWT using a provided secret key.
     *
     * @param signedJWT the SignedJWT to verify the signature of
     * @throws JOSEException if the HMAC validation fails
     */
    private void verifySignedJWT(SignedJWT signedJWT) throws JOSEException {
        if (!signedJWT.verify(new MACVerifier(this.getSecretKey()))) {
            throw new JOSEException("HMAC validation failed");
        }
    }

    /**
     * Returns the SecretKey used for signing JSON Web Tokens (JWTs).
     */
    private SecretKey getSecretKey() {
        return new SecretKeySpec(secretKey.getBytes(), SECRET_KEY_ALG);
    }

    /**
     * Returns the HMAC signet used for signing JSON Web Tokens (JWTs).
     */
    private JWSSigner getHMACSigner() {
        try {
            return new MACSigner(this.getSecretKey().getEncoded());
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the JSON Web Encryption (JWE) header object.
     * The header object is constructed using the A256GCMKW algorithm and A256GCM encryption method.
     *
     * @return the JWEHeader object
     */
    private JWEHeader getJWEHeader() {
        return new JWEHeader.Builder(JWEAlgorithm.A256GCMKW, EncryptionMethod.A256GCM).build();
    }

    /**
     * Creates the claims for a JSON Web Token (JWT) based on the provided UserDetails.
     *
     * @param userDetails The UserDetails object containing the user details.
     * @return The JWTClaimsSet object containing the claims for the JWT.
     */
    private JWTClaimsSet createClaims(UserDetails userDetails) {
        return new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(new Date().getTime() + expiration))
                .build();
    }

    /**
     * Signs a JWT using the specified claims set.
     *
     * @param jwtClaimsSet the claims set to be included in the JWT
     * @return a signed JWT
     * @throws RuntimeException if an error occurs while signing the JWT
     */
    private SignedJWT signJWT(JWTClaimsSet jwtClaimsSet) {
        JWSSigner signer = this.getHMACSigner();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSet);

        try {
            signedJWT.sign(signer);
            return signedJWT;
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a JSON Web Token (JWT) for the given user details.
     *
     * @param userDetails The user details used to generate the token.
     * @return The generated token.
     * @throws JOSEException If an error occurs during the token generation.
     */
    public String generateToken(UserDetails userDetails) throws JOSEException {
        JWTClaimsSet jwtClaimsSet = this.createClaims(userDetails);
        SignedJWT signedJWT = this.signJWT(jwtClaimsSet);

        JWEObject jweObject = new JWEObject(this.getJWEHeader(), new Payload(signedJWT));
        jweObject.encrypt(new DirectEncrypter(this.getSecretKey().getEncoded()));

        return jweObject.serialize();
    }
}
