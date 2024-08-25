package com.mv.streamingservice.content.dto.request;

import com.mv.streamingservice.content.enums.Resolution;

import java.util.UUID;

public record MediaLinkRequest(
        UUID id,
        String link,
        Resolution resolution
) {
}
