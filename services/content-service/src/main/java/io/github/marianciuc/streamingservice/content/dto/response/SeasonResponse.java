package com.mv.streamingservice.content.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SeasonResponse(
        UUID id,
        UUID contentId,
        Integer seasonNumber,
        String seasonTitle,
        LocalDateTime seasonReleaseDate,
        List<EpisodeResponse> episodes
) {
}
