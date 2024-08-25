package io.github.marianciuc.streamingservice.content.mappers;

import io.github.marianciuc.streamingservice.content.dto.request.GenreRequest;
import io.github.marianciuc.streamingservice.content.dto.response.GenreResponse;
import io.github.marianciuc.streamingservice.content.entity.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper implements GenericMapper<Genre, GenreResponse, GenreRequest> {

    public GenreResponse toDto(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Cannot map to DTO as genre is null");
        }
        return new GenreResponse(genre.getId(), genre.getName(), genre.getDescription());
    }

    public Genre toEntity(GenreRequest genreRequest) {
        return Genre.builder()
                .id(genreRequest.id())
                .name(genreRequest.name())
                .description(genreRequest.description())
                .build();
    }
}
