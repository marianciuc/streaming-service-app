/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryRepository.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.CategoryDto;
import io.github.marianciuc.streamingservice.moderation.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto add(CategoryDto categoryDto);
    void delete(String categoryId);
    Category find(UUID id);
    List<CategoryDto> findAll();
}
