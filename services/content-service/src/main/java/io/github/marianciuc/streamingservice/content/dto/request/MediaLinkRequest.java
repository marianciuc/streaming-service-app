package io.github.marianciuc.streamingservice.content.dto.request;


import io.github.marianciuc.streamingservice.content.enums.Resolution;

import java.util.UUID;

public record MediaLinkRequest(
        UUID id,
        String link,
        Resolution resolution
) {
}
