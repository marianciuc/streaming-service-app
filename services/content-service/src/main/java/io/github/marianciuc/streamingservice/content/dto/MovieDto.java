/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieDto.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import io.github.marianciuc.streamingservice.content.entity.Movie;

import java.util.UUID;

public record MovieDto(
        UUID id,
        UUID contentId,
        Integer duration,
        String masterPlaylistUrl,
        UUID masterPlaylistId
) {
    public static MovieDto toMovieDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getContent().getId(),
                movie.getDuration(),
                movie.getMasterPlaylistUrl(),
                movie.getMasterPlaylistId()
        );
    }
}
