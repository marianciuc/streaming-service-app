/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.CategoryDto;
import io.github.marianciuc.streamingservice.moderation.entity.Category;
import io.github.marianciuc.streamingservice.moderation.repositories.CategoryRepository;
import io.github.marianciuc.streamingservice.moderation.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;


    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void delete(String categoryId) {

    }

    @Override
    public Category find(UUID id) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return List.of();
    }
}
