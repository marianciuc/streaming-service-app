package io.github.marianciuc.streamingservice.content.dto.response;


import io.github.marianciuc.streamingservice.content.enums.ContentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ContentResponse(
        UUID id,
        String description,
        ContentType contentType,
        LocalDateTime releaseDate,
        Integer duration,
        double rating,
        String ageRating,
        String posterUrl,
        List<GenreResponse> genres
) {
}
