package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.response.MovieResponse;
import io.github.marianciuc.streamingservice.content.entity.Movie;
import io.github.marianciuc.streamingservice.content.mappers.MovieMapper;
import io.github.marianciuc.streamingservice.content.repository.MovieRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie to retrieve
     * @return the MovieResponse object representing the movie with the given ID
     * @throws NotFoundException if the movie with the given ID is not found
     */
    public MovieResponse getMovieById(UUID id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
        return movieMapper.toDto(movie);
    }

    /**
     * Retrieves a movie by its content ID.
     *
     * @param contentId the ID of the content associated with the movie to retrieve
     * @return the MovieResponse object representing the movie with the given content ID
     * @throws NotFoundException if the movie with the given content ID is not found
     */
    public MovieResponse getMovieByContentId(UUID contentId) {
        Movie movie = movieRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
        return movieMapper.toDto(movie);
    }

    public Movie getMovieEntityByContentId(UUID contentId) {
        return movieRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
    }
}
