/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenUser.java
 *
 */

package io.github.marianciuc.streamingservice.user.entity;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class JWTUserPrincipal extends User implements UserPrincipal {

    @Getter
    private final Token token;
    private final UUID id;


    public JWTUserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Token token, UUID id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.token = token;
        this.id = id;
    }


    @Override
    public UUID getId() {
        return id;
    }

}
