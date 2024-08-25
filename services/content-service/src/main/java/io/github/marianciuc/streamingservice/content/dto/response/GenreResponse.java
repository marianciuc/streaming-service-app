package com.mv.streamingservice.content.dto.response;

import java.util.UUID;

public record GenreResponse(
        UUID id,
        String name,
        String description
) {
}
