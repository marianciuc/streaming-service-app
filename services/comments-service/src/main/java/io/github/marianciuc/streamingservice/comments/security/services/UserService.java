/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserService.java
 *
 */

package io.github.marianciuc.streamingservice.comments.security.services;

import java.util.UUID;

public interface UserService {
    UUID extractUserIdFromAuth();
    boolean hasAdminRoles();
}
