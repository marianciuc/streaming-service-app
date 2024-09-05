/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ContentDto.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.enums.ContentType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ContentDto(
        UUID id,
        String title,
        String description,
        ContentType type,
        LocalDate releaseDate,
        String ageRating,
        String posterUrl,
        List<GenreDto> genreList,
        List<PersonDto> directorList,
        List<SeasonDto> seasonList,
        List<PersonDto> actorList,
        List<TagDto> tagList
) {
    public static ContentDto toContentDto(Content content) {
        return new ContentDto(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getContentType(),
                content.getReleaseDate(),
                content.getAgeRating(),
                content.getPosterUrl(),
                content.getGenres().stream().map(GenreDto::toGenreDto).toList(),
                content.getDirectors().stream().map(PersonDto::toPersonDto).toList(),
                content.getSeasons().stream().map(SeasonDto::toSeasonDto).toList(),
                content.getActors().stream().map(PersonDto::toPersonDto).toList(),
                content.getTags().stream().map(TagDto::toTagDto).toList()
        );
    }
}
