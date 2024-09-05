/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagsController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/api/v1/content/tags")
@RequiredArgsConstructor
public class TagsController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<UUID> createTag(@RequestParam("tag") String tagName) {
        return ResponseEntity.ok(tagService.createTag(tagName));
    }

    @PutMapping("/{tag-id}")
    public ResponseEntity<Void> updateTag(@PathVariable("tag-id") UUID id, @RequestParam("tag") String tagName) {
        tagService.updateTag(id, tagName);
        return ResponseEntity.ok().build();
    }
}
