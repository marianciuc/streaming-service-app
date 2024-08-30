/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ImageDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import org.springframework.core.io.ByteArrayResource;

public record ImageDto(
        ByteArrayResource byteArrayResource,
        String contentType
) {
}
