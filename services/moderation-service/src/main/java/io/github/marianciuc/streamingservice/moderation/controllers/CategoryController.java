/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryController.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.controllers;

import io.github.marianciuc.streamingservice.moderation.dto.requests.CategoryRequest;
import io.github.marianciuc.streamingservice.moderation.dto.responses.CategoryResponse;
import io.github.marianciuc.streamingservice.moderation.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/moderation/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.add(request));
    }


    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
