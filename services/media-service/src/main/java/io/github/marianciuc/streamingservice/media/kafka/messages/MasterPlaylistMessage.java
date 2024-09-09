/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MasterPlaylistCreatedMessage.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka.messages;

import io.github.marianciuc.streamingservice.media.enums.MediaType;

import java.util.UUID;

public record MasterPlaylistMessage(
        UUID contentId,
        MediaType mediaType,
        UUID masterPlaylistId,
        String masterPlaylistUrl
) { }
