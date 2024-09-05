/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.MovieDto;

import java.util.UUID;

public interface MovieService {
    UUID createMovie(MovieDto movieDto);
    MovieDto getMovie(UUID movieId);
    void updateMovieMasterPlaylist(CreateMasterPlayListMessage message);
    void deleteMovie(UUID movieId);
    void getMovieById(UUID movieId);
}
