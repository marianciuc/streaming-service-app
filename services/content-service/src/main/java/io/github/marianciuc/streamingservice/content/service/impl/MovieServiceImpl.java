/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.kafka.messages.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.MovieDto;
import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Movie;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.kafka.KafkaMediaProducer;
import io.github.marianciuc.streamingservice.content.repository.MovieRepository;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import io.github.marianciuc.streamingservice.content.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final KafkaMediaProducer kafkaMediaProducer;
    private final ContentService contentService;
    private final MovieRepository repository;

    @Override
    public UUID createMovie(MovieDto movieDto) {
        Content content = contentService.getContentEntity(movieDto.contentId());

        Movie movie = Movie.builder()
                .duration(movieDto.duration())
                .recordStatus(RecordStatus.HIDDEN)
                .content(content)
                .build();
        return repository.save(movie).getId();
    }

    @Override
    public MovieDto getMovie(UUID movieId) {
        return MovieDto.toMovieDto(this.getMovieEntity(movieId));
    }

    @Override
    public void updateMovieMasterPlaylist(CreateMasterPlayListMessage message) {
        Movie movie = this.getMovieEntity(message.contentId());
        movie.setMasterPlaylistId(message.masterPlaylistId());

        if (!message.url().isEmpty()) {
            movie.setMasterPlaylistUrl(message.url());
            movie.setRecordStatus(RecordStatus.ACTIVE);
        }
        repository.save(movie);
    }

    @Override
    public void deleteMovie(UUID movieId) {
        kafkaMediaProducer.sendDeleteMediaMessage(movieId);
        repository.deleteById(movieId);
    }

    private Movie getMovieEntity(UUID contentId) {
        return repository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
    }
}
