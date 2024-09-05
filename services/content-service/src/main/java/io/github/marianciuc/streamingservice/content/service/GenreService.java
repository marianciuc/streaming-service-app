/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: GenreService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.GenreDto;
import io.github.marianciuc.streamingservice.content.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    UUID createGenre(GenreDto genreDto);
    GenreDto getGenre(UUID id);
    void updateGenre(UUID id, GenreDto genreDto);
    void deleteGenre(UUID id);
    Genre getGenreEntity(UUID id);
    List<GenreDto> getAllGenres();
}
