/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateUserMessage.java
 *
 */

package io.github.marianciuc.streamingservice.user.kafka.messages;

import java.util.UUID;

public record CreateUserMessage(
        UUID id,
        String email,
        String username
) {
}
