/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ContentService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.ContentDto;
import io.github.marianciuc.streamingservice.content.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing content.
 */
public interface ContentService {

    /**
     * Creates a new content by the given content DTO.
     * @param contentDto the content DTO
     * @return the ID of the created content
     * @throws NotFoundException if the genre, director or actors associated with the content is not found
     */
    UUID createContent(ContentDto contentDto);

    /**
     * Retrieves a content by its ID.
     * @param contentId the ID of the content to retrieve
     * @return the ContentDto object representing the content with the given ID
     * @throws NotFoundException if the content with the given ID is not found
     */
    ContentDto getContent(UUID contentId);

    /**
     * Updates the content with the given ID by the given content DTO. Updates only the fields that are not null.
     * It is not possible to add a new series, movie or seasons using this method. Use the EpisodeService, MovieService, or SeasonService for this purpose.
     * @param contentId the ID of the content to update
     * @param contentDto the content DTO
     * @throws NotFoundException if the content with the given ID is not found
     * @see EpisodeService
     * @see MovieService
     * @see SeasonService
     */
    void updateContent(UUID contentId, ContentDto contentDto);

    /**
     * Deletes the content if the content has no active links with movies or seasons. If the content has active links, the content with seasons, episodes, movie will be marked as deleted.
     * @param contentId the ID of the content to delete
     * @throws NotFoundException if the content with the given ID is not found
     */
    void deleteContent(UUID contentId);

    /**
     * Retrieves a content entity by its ID.
     * @param contentId the ID of the content entity to retrieve
     * @return the Content object representing the content entity with the given ID
     * @throws NotFoundException if the content entity with the given ID is not found
     */
    Content getContentEntity(UUID contentId);

    /**
     * Retrieves all content by the given filters.
     * @param title the title of the content
     * @param genreId the ID of the genre associated with the content
     * @param directorId the ID of the director associated with the content
     * @param page the page number
     * @param pageSize the page size
     * @param actorId the ID of the actor associated with the content
     * @param ageRating the age rating of the content
     * @param releaseDateYear the release date year of the content in the format "yyyy"
     * @param contentType the type of the content
     * @param recordStatus the status of the content
     * @param orderByDateDesk the order by date descending
     * @return the PaginationResponse object representing the list of content
     * @see ContentType
     * @see RecordStatus
     * @see PaginationResponse
     * @see ContentDto
     */
    PaginationResponse<List<ContentDto>> getAllContentByFilters(
            String title,
            UUID genreId,
            UUID directorId,
            Integer page,
            Integer pageSize,
            UUID actorId,
            String ageRating,
            String releaseDateYear,
            ContentType contentType,
            RecordStatus recordStatus,
            Boolean orderByDateDesk
    );
}
