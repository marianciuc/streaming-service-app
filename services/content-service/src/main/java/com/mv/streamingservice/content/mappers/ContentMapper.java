package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.ContentRequest;
import com.mv.streamingservice.content.dto.response.ContentResponse;
import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentMapper implements GenericMapper<Content, ContentResponse, ContentRequest> {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Override
    public ContentResponse toDto(Content entity) {
        return new ContentResponse(
                entity.getId(),
                entity.getDescription(),
                entity.getContentType(),
                entity.getReleaseDate(),
                entity.getDuration(),
                entity.getRating(),
                entity.getAgeRating(),
                entity.getPosterUrl(),
                entity.getGenres().stream().map(genreMapper::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public Content toEntity(ContentRequest dto) {
        return Content.builder()
                .id(dto.id())
                .title(dto.title())
                .ageRating(dto.ageRating())
                .description(dto.description())
                .contentType(dto.contentType())
                .duration(dto.duration())
                .genres(dto.genresIds().stream().map(genreService::findGenreEntityById).collect(Collectors.toList()))
                .posterUrl(dto.posterUrl())
                .build();
    }
}
