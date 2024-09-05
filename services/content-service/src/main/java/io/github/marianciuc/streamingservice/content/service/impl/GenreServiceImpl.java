/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: GenreServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.GenreDto;
import io.github.marianciuc.streamingservice.content.entity.Genre;
import io.github.marianciuc.streamingservice.content.repository.GenreRepository;
import io.github.marianciuc.streamingservice.content.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public UUID createGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public GenreDto getGenre(UUID id) {
        return null;
    }

    @Override
    public void updateGenre(UUID id, GenreDto genreDto) {

    }

    @Override
    public void deleteGenre(UUID id) {

    }

    @Override
    public Genre getGenreEntity(UUID id) {
        return null;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return List.of();
    }
}
