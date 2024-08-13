package com.mv.streamingservice.content.service;

import com.mv.streamingservice.content.dto.request.GenreRequest;
import com.mv.streamingservice.content.dto.response.GenreResponse;
import com.mv.streamingservice.content.dto.response.PaginationResponse;
import com.mv.streamingservice.content.entity.Genre;
import com.mv.streamingservice.content.enums.RecordStatus;
import com.mv.streamingservice.content.mappers.GenreMapper;
import com.mv.streamingservice.content.mappers.PageMapper;
import com.mv.streamingservice.content.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;


/**
 * The GenreService class is responsible for handling genre-related operations such as creating, finding, updating, and retrieving genres.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final PageMapper pageMapper;

    /**
     * Creates a new genre based on the provided genre request.
     *
     * @param request The genre request containing the information for the new genre.
     * @return The ID of the newly created genre.
     */
    public UUID createGenre(GenreRequest request) {
        Genre genre = genreMapper.toEntity(request);
        genre.setRecordStatus(RecordStatus.ACTIVE);
        return genreRepository.save(genre).getId();
    }

    /**
     * Finds a genre by its ID.
     *
     * @param id The ID of the genre to find.
     * @return The GenreResponse object containing the genre information.
     * @throws NoSuchElementException if the genre with the given ID is not found.
     */
    public GenreResponse findById(UUID id) {
        return genreRepository
                .findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(
                        () -> new NoSuchElementException("Genre not found :: " + id.toString())
                );
    }

    /**
     * Updates the genre with the given ID based on the provided genre request.
     *
     * @param id            The ID of the genre to update.
     * @param genreRequest  The genre request containing the updated data.
     * @return The updated genre's ID.
     * @throws NoSuchElementException if the genre with the given ID is not found.
     */
    public UUID updateGenre(UUID id, GenreRequest genreRequest) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Genre not found :: " + id.toString()));

        if (!Objects.equals(genreRequest.name(), genre.getName())) genre.setName(genreRequest.name());
        if (!Objects.equals(genreRequest.description(), genre.getDescription()))
            genre.setDescription(genreRequest.description());

        return genreRepository.save(genre).getId();
    }

    /**
     * Finds all genres based on the provided parameters and returns a PaginatedResponse containing GenreResponse objects.
     *
     * @param name The name of the genre to filter by (optional).
     * @param page The page number (starting from 0).
     * @param size The number of genres per page.
     * @return A PaginatedResponse object containing GenreResponse objects.
     */
    public PaginationResponse<GenreResponse> findAll(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Genre> genrePage;
        if (name != null) {
            genrePage = genreRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            genrePage = genreRepository.findAll(pageable);
        }
        return pageMapper.mapEntityPageIntoDtoPage(genrePage, genreMapper);
    }

    /**
     * Deletes a genre by its ID. Sets the record status of the genre to DELETED.
     *
     * @param id The ID of the genre to delete.
     * @throws NoSuchElementException If the genre is not found.
     */
    public void deleteById(UUID id) {
        log.info("Deleting genre : {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Genre not found :: " + id.toString()));
        genre.setRecordStatus(RecordStatus.DELETED);
        genreRepository.save(genre);
    }
}
