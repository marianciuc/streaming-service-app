package io.github.marianciuc.streamingservice.content.dto.response;

import io.github.marianciuc.streamingservice.content.enums.Resolution;

import java.util.UUID;

public record MediaLinkResponse(
        UUID id,
        Resolution resolution,
        String link
) {
}
