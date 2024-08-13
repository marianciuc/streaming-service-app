package com.mv.streamingservice.content.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EpisodeResponse(
        UUID id,
        Integer episodeNumber,
        String title,
        LocalDateTime releaseDate,
        String description,
        List<MediaLinkResponse> mediaLinks
) {
}
