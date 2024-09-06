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
import io.github.marianciuc.streamingservice.content.exceptions.InvalidTagNameException;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.exceptions.TagAlreadyExistsException;

import java.util.List;
import java.util.UUID;

/**
 * Tag service interface
 */
public interface TagService {

    /**
     * Create a new tag with the given name
     * @param tag tag name
     * @return tag id
     * @throws InvalidTagNameException if tag is null or empty
     * @throws TagAlreadyExistsException if tag already exists
     */
    UUID createTag(String tag);

    /**
     * Delete a tag from database by id
     * @param id tag id
     * @throws NotFoundException if tag not found
     */
    void deleteTag(UUID id);


    /**
     * Update tag name
     * @param id tag id
     * @param newTag new tag name
     * @throws NotFoundException if tag not found
     * @throws InvalidTagNameException if tag is null or empty
     */
    void updateTag(UUID id, String newTag);

    /**
     * Find tag by name
     * @param prefix tag name
     * @return List of TagDto or empty list
     */
    List<TagDto> findTagByNamePrefix(String prefix);

    /**
     * Find tag by id
     * @param id tag id
     * @return Tag entity
     */
    Tag findTagById(UUID id);
}
