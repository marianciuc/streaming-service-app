package com.mv.streamingservice.content.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SeasonRequest(
        UUID id,
        UUID contentId,
        Integer seasonNumber,
        String seasonTitle,
        LocalDateTime releaseDate,
        List<UUID> episodes
) {
}
