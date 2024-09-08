/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserPrincipal.java
 *
 */

package io.github.marianciuc.streamingservice.user.entity;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserPrincipal extends UserDetails {
    UUID getId();
}
