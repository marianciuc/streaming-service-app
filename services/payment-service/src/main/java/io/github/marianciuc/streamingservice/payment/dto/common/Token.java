/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Token.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.common;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Token(
        UUID tokenId,
        UUID userId,
        String subject,
        String issuer,
        List<String> roles,
        Instant createdAt,
        Instant expiresAt
) {}
