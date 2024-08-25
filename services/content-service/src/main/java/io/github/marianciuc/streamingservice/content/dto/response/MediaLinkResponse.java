package com.mv.streamingservice.content.dto.response;

import com.mv.streamingservice.content.enums.Resolution;

import java.util.UUID;

public record MediaLinkResponse(
        UUID id,
        Resolution resolution,
        String link
) {
}
