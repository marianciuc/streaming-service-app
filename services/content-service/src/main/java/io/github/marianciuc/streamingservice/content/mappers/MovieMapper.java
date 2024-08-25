package io.github.marianciuc.streamingservice.content.mappers;

import io.github.marianciuc.streamingservice.content.dto.request.MovieRequest;
import io.github.marianciuc.streamingservice.content.dto.response.MovieResponse;
import io.github.marianciuc.streamingservice.content.entity.Movie;
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
