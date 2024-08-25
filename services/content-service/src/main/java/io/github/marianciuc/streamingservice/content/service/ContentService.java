package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.request.ContentRequest;
import io.github.marianciuc.streamingservice.content.dto.response.ContentResponse;
import io.github.marianciuc.streamingservice.content.dto.response.MovieResponse;
import io.github.marianciuc.streamingservice.content.dto.response.PaginationResponse;
import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Genre;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.mappers.ContentMapper;
import io.github.marianciuc.streamingservice.content.mappers.PageMapper;
import io.github.marianciuc.streamingservice.content.repository.ContentRepository;
import io.github.marianciuc.streamingservice.content.specifications.ContentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The ContentService class provides methods to manage content items.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final PageMapper pageMapper;
    private final GenreService genreService;
    private final MovieService movieService;

    private static final String CONTENT_NOT_FOUND_ERROR = "Content service error :: content not found :: ";


    /**
     * Finds content by ID.
     *
     * @param id the ID of the content
     * @return the content response
     * @throws NotFoundException if the content with the specified ID is not found
     */
    public ContentResponse findContentById(UUID id) {
        return contentRepository
                .findById(id)
                .map(contentMapper::toDto)
                .orElseThrow(
                        () -> new NotFoundException("Content not found :: " + id)
                );
    }

    /**
     * Finds content items based on the given criteria.
     *
     * @param page    the page number (starts from 0)
     * @param size    the number of items per page
     * @param title   the title to search for (optional)
     * @param type    the content type to filter by (optional)
     * @param genreId the genre ID to filter by (optional)
     * @return a PaginationResponse object containing the content items that match the criteria
     */
    public PaginationResponse<ContentResponse> findContent(Integer page, Integer size, String title, ContentType type, UUID genreId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        Specification<Content> spec = Specification.where(null);
        if (title != null) spec = spec.and(ContentSpecification.titleContains(title));
        if (type != null) spec = spec.and(ContentSpecification.typeIs(type));
        if (genreId != null) spec = spec.and(ContentSpecification.genreIdIs(genreId));

        Page<Content> contentPage = contentRepository.findAll(spec, pageable);
        return pageMapper.mapEntityPageIntoDtoPage(contentPage, contentMapper);
    }

    /**
     * Creates a new content with the specified request.
     *
     * @param request the content request containing the details of the content to create
     * @return the generated UUID of the created content
     */
    public UUID createContent(ContentRequest request) {
        Content content = contentMapper.toEntity(request);
        content.setRecordStatus(RecordStatus.HIDDEN);
        return contentRepository.save(content).getId();
    }

    /**
     * Deletes the content with the specified ID by setting its record status DELETED.
     *
     * @param id the ID of the content to delete
     * @throws NotFoundException if the content with the specified ID is not found
     */
    public void deleteContent(UUID id) {
        Content content = getContentById(id);
        content.setRecordStatus(RecordStatus.DELETED);
    }

    /**
     * Updates the content with the given ID using the provided request.
     *
     * @param id      the ID of the content to update
     * @param request the content request containing the updated fields
     * @return the ID of the updated content
     * @throws NotFoundException if the content with the given ID is not found or if it has been deleted
     */
    public UUID updateContent(UUID id, ContentRequest request) {
        Content content = getContentById(id);

        updateContentFields(request, content);
        updateGenreIfChanged(content, request);

        return contentRepository.save(content).getId();
    }

    /**
     * Checks if the given newValue is different from the currentValue.
     *
     * @param newValue     The new value to be checked.
     * @param currentValue The current value to be compared with.
     * @return True if the newValue is different from the currentValue, false otherwise.
     */
    private <T> boolean hasValueChanged(T newValue, T currentValue) {
        return !newValue.equals(currentValue);
    }


    /**
     * Updates the genre of the given content if it has changed in the content request.
     *
     * @param content The content object to update.
     * @param request The content request object containing the updated genre information.
     */
    private void updateGenreIfChanged(Content content, ContentRequest request) {
        List<UUID> originalGenreIds = content.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
        List<UUID> addedGenreIds = getAddedGenreIds(request, originalGenreIds);
        List<UUID> removedGenreIds = getRemovedGenreIds(request, originalGenreIds);

        if (!removedGenreIds.isEmpty() || !addedGenreIds.isEmpty()) {
            List<Genre> genres = request.genresIds().stream().map(genreService::findGenreEntityById).collect(Collectors.toList());
            content.setGenres(genres);
        }
    }

    /**
     * Returns the list of added genre IDs based on the given Content, ContentRequest, and original genre IDs.
     *
     * @param request          the ContentRequest object
     * @param originalGenreIds the list of original genre IDs
     * @return the list of added genre IDs
     */
    private List<UUID> getAddedGenreIds(ContentRequest request, List<UUID> originalGenreIds) {
        return request.genresIds().stream()
                .filter(clientGenreId -> !originalGenreIds.contains(clientGenreId)).collect(Collectors.toList());
    }

    /**
     * Retrieves the list of removed genre IDs.
     *
     * @param request          The content request object containing the updated genre IDs.
     * @param originalGenreIds The list of original genre IDs.
     * @return The list of genre IDs that have been removed from the original list.
     */
    private List<UUID> getRemovedGenreIds(ContentRequest request, List<UUID> originalGenreIds) {
        return originalGenreIds.stream()
                .filter(originalGenreId -> !request.genresIds().contains(originalGenreId)).collect(Collectors.toList());
    }

    /**
     * This method updates the fields of a Content object based on the values in a ContentRequest object.
     *
     * @param request The ContentRequest object containing the new field values.
     * @param content The Content object that needs to be updated.
     */
    private void updateContentFields(ContentRequest request, Content content) {
        updateIfValueChanged(request.recordStatus(), content.getRecordStatus(), content::setRecordStatus);
        updateIfValueChanged(request.description(), content.getDescription(), content::setDescription);
        updateIfValueChanged(request.title(), content.getTitle(), content::setTitle);
        updateIfValueChanged(request.ageRating(), content.getAgeRating(), content::setAgeRating);
        updateIfValueChanged(request.posterUrl(), content.getPosterUrl(), content::setPosterUrl);
        updateIfValueChanged(request.duration(), content.getDuration(), content::setDuration);
        updateIfValueChanged(request.releaseDate(), content.getReleaseDate(), content::setReleaseDate);
    }

    /**
     * Updates a value if it has changed by comparing it with the existing value
     * and invoking a setter function to update the value.
     *
     * @param requestValue The new value requested to be set
     * @param entityValue  The current value of the entity
     * @param setter       The function to set the new value
     * @param <T>          The type of the value
     */
    private <T> void updateIfValueChanged(T requestValue, T entityValue, Consumer<T> setter) {
        if (hasValueChanged(requestValue, entityValue)) {
            setter.accept(requestValue);
        }
    }

    /**
     * Retrieves the movie content with the specified ID.
     *
     * @param id the ID of the movie content to retrieve
     * @return the movie response containing the movie content
     * @throws NotFoundException if the movie content with the specified ID is not found
     */
    public MovieResponse getMovieContent(UUID id) {
        Content content = getContentById(id);
        assertContentTypeIsMovie(content);
        return fetchContentMovie(id);
    }

    /**
     * Asserts that the content type of the given content is "MOVIE".
     * Throws an InvalidParameterException if the content type is not "MOVIE".
     *
     * @param content The content to be checked.
     * @throws InvalidParameterException if the content type is not "MOVIE".
     */
    public void assertContentTypeIsMovie(Content content) {
        if (!content.getContentType().equals(ContentType.MOVIE)) {
            throw new InvalidParameterException("Content is not a movie :: " + content.getId());
        }
    }

    /**
     * Asserts that the content type of the given content is a series.
     *
     * @param content The content to check.
     * @throws InvalidParameterException If the content type is not a series.
     */
    public void assertContentTypeIsSeries(Content content) {
        if (!content.getContentType().equals(ContentType.SERIES)) {
            throw new InvalidParameterException("Content is not a series :: " + content.getId());
        }
    }

    /**
     * Fetches a movie from the movie service based on the specified content ID.
     *
     * @param id The UUID of the content to fetch the movie for.
     * @return The movie response object corresponding to the specified content ID.
     * @throws NotFoundException If the movie corresponding to the content ID is not found.
     */
    private MovieResponse fetchContentMovie(UUID id) {
        return Optional.ofNullable(movieService.getMovieByContentId(id))
                .orElseThrow(() -> new NotFoundException(CONTENT_NOT_FOUND_ERROR + id));
    }


    /**
     * Retrieves the content by its unique identifier.
     *
     * @param contentId the unique identifier of the content
     * @return the content with the specified identifier
     * @throws NotFoundException if the content with the given identifier is not found or has been deleted
     */
    public Content getContentById(UUID contentId){
        String missingContentError = CONTENT_NOT_FOUND_ERROR + contentId;
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException(missingContentError));
        if (content.isRecordStatusDeleted())
            throw new NotFoundException(missingContentError);
        return content;
    }

}
