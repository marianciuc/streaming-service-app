package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.GenreRequest;
import com.mv.streamingservice.content.dto.response.GenreResponse;
import com.mv.streamingservice.content.entity.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreMapper implements GenericMapper<Genre, GenreResponse, GenreRequest> {

    public GenreResponse toDto(Genre genre) {
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
