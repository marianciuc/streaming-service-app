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
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.repository.GenreRepository;
import io.github.marianciuc.streamingservice.content.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Override
    public UUID createGenre(GenreDto genreDto) {
        Genre genre = Genre.builder()
                .title(genreDto.title())
                .description(genreDto.description())
                .recordStatus(RecordStatus.ACTIVE)
                .build();
        return repository.save(genre).getId();
    }

    @Override
    public GenreDto getGenre(UUID id) {
        return GenreDto.toGenreDto(this.getGenreEntity(id));
    }

    @Override
    public void updateGenre(UUID id, GenreDto genreDto) {
        Genre genre = this.getGenreEntity(id);
        if (!genreDto.title().isEmpty()) genre.setTitle(genreDto.title());
        if (!genreDto.description().isEmpty()) genre.setDescription(genreDto.description());
        repository.save(genre);
    }

    @Override
    public void deleteGenre(UUID id) {
        Genre genre = this.getGenreEntity(id);
        repository.delete(genre);
    }

    @Override
    public Genre getGenreEntity(UUID id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Genre not found"));
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return repository.findAll().stream().map(GenreDto::toGenreDto).toList();
    }
}
