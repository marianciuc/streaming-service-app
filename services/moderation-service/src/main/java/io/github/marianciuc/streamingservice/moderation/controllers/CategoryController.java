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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam(value = "isDeleted", defaultValue =
            "false") Boolean isDeleted) {
        return ResponseEntity.ok(categoryService.findAll(isDeleted));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") UUID categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
