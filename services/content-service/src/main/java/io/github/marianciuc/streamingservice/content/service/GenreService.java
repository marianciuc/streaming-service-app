package io.github.marianciuc.streamingservice.content.service;


import io.github.marianciuc.streamingservice.content.dto.request.GenreRequest;
import io.github.marianciuc.streamingservice.content.dto.response.GenreResponse;
import io.github.marianciuc.streamingservice.content.dto.response.PaginationResponse;
import io.github.marianciuc.streamingservice.content.entity.Genre;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.mappers.GenreMapper;
import io.github.marianciuc.streamingservice.content.mappers.PageMapper;
import io.github.marianciuc.streamingservice.content.repository.GenreRepository;
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
     * Finds the GenreResponse for the given genre Id.
     *
     * @param id the Id of the genre
     * @return the GenreResponse corresponding to the given Id
     */
    public GenreResponse findGenreResponseById(UUID id) {
        Genre genre = getGenreById(id);
        return genreMapper.toDto(genre);
    }

    /**
     * Finds a genre entity in the database by its ID.
     *
     * @param id The ID of the genre entity.
     * @return The genre entity associated with the given ID, or null if no such entity exists.
     */
    public Genre findGenreEntityById(UUID id) {
        return getGenreById(id);
    }

    /**
     * Retrieves the genre with the specified ID from the genre repository.
     *
     * @param id the ID of the genre to retrieve
     * @return the genre with the specified ID
     * @throws NoSuchElementException if no genre is found with the specified ID
     */
    private Genre getGenreById(UUID id) {
        return genreRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Genre not found :: " + id)
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
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Genre not found :: " + id));

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
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Genre not found :: " + id));
        genre.setRecordStatus(RecordStatus.DELETED);
        genreRepository.save(genre);
    }
}
