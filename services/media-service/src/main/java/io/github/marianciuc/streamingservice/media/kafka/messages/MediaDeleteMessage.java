/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaDeleteMessage.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka.messages;

import java.util.UUID;

public record MediaDeleteMessage(
        UUID contentId
) {
}
