package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.MovieRequest;
import com.mv.streamingservice.content.dto.response.MovieResponse;
import com.mv.streamingservice.content.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieMapper implements GenericMapper<Movie, MovieResponse, MovieRequest> {

    private final MediaLinkMapper mediaLinkMapper;

    @Override
    public MovieResponse toDto(Movie entity) {
        return new MovieResponse(
                entity.getId(),
                entity.getContent().getId(),
                entity.getMediaLink().stream().map(mediaLinkMapper::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public Movie toEntity(MovieRequest dto) {
        return null;
    }
}
