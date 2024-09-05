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

import java.util.List;
import java.util.UUID;

public interface ContentService {

    UUID createContent(ContentDto contentDto);

    ContentDto getContent(UUID contentId);

    void updateContent(UUID contentId, ContentDto contentDto);

    void deleteContent(UUID contentId);

    Content getContentEntity(UUID contentId);

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
