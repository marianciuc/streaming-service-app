/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.MovieDto;
import io.github.marianciuc.streamingservice.content.kafka.KafkaMediaProducer;
import io.github.marianciuc.streamingservice.content.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final KafkaMediaProducer kafkaMediaProducer;

    @Override
    public UUID createMovie(MovieDto movieDto) {

        return null;
    }

    @Override
    public MovieDto getMovie(UUID movieId) {
        return null;
    }

    @Override
    public void updateMovieMasterPlaylist(CreateMasterPlayListMessage message) {

    }

    @Override
    public void deleteMovie(UUID movieId) {
        kafkaMediaProducer.sendDeleteMediaMessage(movieId);
    }

    @Override
    public void getMovieById(UUID movieId) {

    }
}
