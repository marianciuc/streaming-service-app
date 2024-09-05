/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateMasterPlayListMessage.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import java.util.UUID;

public record CreateMasterPlayListMessage (
        UUID contentId,
        UUID masterPlaylistId,
        String url
) {
}
