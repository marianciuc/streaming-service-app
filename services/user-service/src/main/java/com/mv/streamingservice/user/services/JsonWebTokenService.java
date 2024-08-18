package com.mv.streamingservice.user.services;

import com.mv.streamingservice.user.entity.User;
import com.mv.streamingservice.user.enums.Role;
import com.mv.streamingservice.user.enums.TokenTag;
import com.mv.streamingservice.user.exceptions.JsonWebTokenExpiredException;
import com.mv.streamingservice.user.exceptions.UnsupportedJsonWebTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * The {@code JsonWebTokenService} class provides functionality to generate JSON Web Tokens (JWT) for authentication.
 * It generates both access and refresh tokens for the specified user. The tokens are signed using a private key and can be used
 * to authenticate requests.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JsonWebTokenService {

    @Value("${security.jwt.token.accessExpiration}")
    private Long accessExpiration;

    @Value("${security.jwt.token.refreshToken}")
    private Long refreshExpiration;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;


    /**
     * Generates an access token for the specified user.
     *
     * @param user The user for whom the access token is generated.
     * @return The generated access token.
     */
    public String generateAccessToken(User user) {
        return generateToken(user, TokenTag.ACCESS_TOKEN);
    }


    /**
     * Generates a refresh token for the given user.
     *
     * @param user the user for whom the refresh token needs to be generated
     * @return the generated refresh token
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, TokenTag.REFRESH_TOKEN);
    }


    /**
     * Generates a token for the given user and token tag.
     *
     * @param user The user for whom the token is generated.
     * @param tokenTag The tag that represents the type of token to generate.
     * @return The generated token as a string.
     */
    private String generateToken(User user, TokenTag tokenTag) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("role", user.getRole())
                .claim("tokenType", tokenTag)
                .expiration(
                        new Date(
                                System.currentTimeMillis() +
                                        (tokenTag.equals(TokenTag.ACCESS_TOKEN) ? accessExpiration : refreshExpiration)
                        )
                )
                .signWith(getPrivateKey())
                .compact();
    }


    /**
     * Retrieves the private key.
     *
     * @return the private key as a SecretKey object
     */
    private SecretKey getPrivateKey() {
        byte[] byteKey = Base64.getDecoder().decode(secretKey.getBytes());
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    /**
     * Parses and validates a JSON Web Token (JWT).
     *
     * @param jwt The JSON Web Token to be parsed and validated.
     * @return The claims payload of the validated JWT.
     * @throws JsonWebTokenExpiredException if the JWT has expired.
     * @throws UnsupportedJsonWebTokenException if the JWT is unsupported.
     */
    private Claims parseJwtAndValidate(String jwt) {
        try {
            JwtParser jwtParser = Jwts
                    .parser()
                    .verifyWith(getPrivateKey())
                    .build();
            return jwtParser.parseSignedClaims(jwt).getPayload();
        } catch (ExpiredJwtException exception) {
            throw new JsonWebTokenExpiredException("Json web token expired");
        } catch (UnsupportedJwtException exception) {
            throw new UnsupportedJsonWebTokenException("Unsupported json web token");
        }
    }

    public TokenTag extractTokenTag(String token) {
        return (TokenTag) parseJwtAndValidate(token).get("tokenType");
    }

    /**
     * Extracts the user ID from a JSON Web Token (JWT).
     *
     * @param jwt The JSON Web Token from which to extract the user ID.
     * @return The user ID extracted from the JWT.
     */
    public UUID extractUserId(String jwt) {
        return UUID.fromString(parseJwtAndValidate(jwt).getSubject());
    }

    /**
     * Extracts the role from a JSON Web Token (JWT).
     *
     * @param jwt The JSON Web Token from which to extract the role.
     * @return The role extracted from the JWT.
     */
    public Role extractRole(String jwt) {
        return (Role) parseJwtAndValidate(jwt).get("role");
    }
}
