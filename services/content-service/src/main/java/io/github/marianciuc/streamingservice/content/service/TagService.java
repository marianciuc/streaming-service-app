/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagsService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.TagDto;
import io.github.marianciuc.streamingservice.content.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {
    UUID createTag(String tag);
    void deleteTag(UUID id);
    void updateTag(UUID id, String newTag);
    List<TagDto> findTagByNamePrefix(String prefix);
    Tag findTagById(UUID id);
}
