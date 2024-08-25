package io.github.marianciuc.streamingservice.content.dto.request;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@Validated
public record EpisodeRequest(
        UUID id,
        UUID seasonId,
        Integer episodeNumber,
        String title,
        LocalDateTime releaseDate,
        String description
) {
}
