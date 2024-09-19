/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: JWTUserPrincipal.java
 *
 */

package io.github.marianciuc.streamingservice.media.security.dto;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

@Getter
public class JWTUserPrincipal extends User {

    private final Token token;
    private final UUID userId;

    public JWTUserPrincipal(Token jwtToken) {
        super(jwtToken.subject(), "", true, true, true, true,
                jwtToken.roles().stream().map(SimpleGrantedAuthority::new).toList());
        this.token = jwtToken;
        this.userId = jwtToken.userId();
    }
}
