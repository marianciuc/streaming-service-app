/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryRepository.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.requests.CategoryRequest;
import io.github.marianciuc.streamingservice.moderation.dto.responses.CategoryResponse;
import io.github.marianciuc.streamingservice.moderation.entity.Category;

import java.util.List;
import java.util.UUID;

/**
 * The category service
 * @since 1.0
 * @see Category
 */
public interface CategoryService {

    /**
     * Add a new category
     * @param request the category request
     * @return the category response
     */
    CategoryResponse add(CategoryRequest request);


    /**
     * Changes the category status to deleted
     * @param categoryId the category id
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if the category is not found
     */
    void delete(UUID categoryId);


    /**
     * Find a category by id
     * @param id the category id
     * @return the category entity
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if the category is not found
     */
    Category find(UUID id);

    /**
     * Find all categories
     * @param isDeleted the category status. If true, return only deleted categories, otherwise return only active
     *                  categories. If null, return all categories
     * @return the list of categories
     */
    List<CategoryResponse> findAll(Boolean isDeleted);

    /**
     * Update a category
     * @param categoryId the category id
     * @param request the category request
     * @param isDeleted the category status (deleted or not). If true, the category will be deleted, otherwise it
     *                  will be restored
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if the category is not found
     */
    void update(UUID categoryId, CategoryRequest request, Boolean isDeleted);
}
