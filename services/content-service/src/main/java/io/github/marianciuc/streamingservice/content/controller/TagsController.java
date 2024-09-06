/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagsController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.TagDto;
import io.github.marianciuc.streamingservice.content.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/api/v1/content/tags")
@RequiredArgsConstructor
public class TagsController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<UUID> createTag(@RequestParam("tag") String tagName) {
        return ResponseEntity.ok(tagService.createTag(tagName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTag(@PathVariable("id") UUID id, @RequestParam("tag") String tagName) {
        tagService.updateTag(id, tagName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(@PathVariable("query") String query) {
        return ResponseEntity.ok(tagService.findTagByNamePrefix(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(TagDto.toTagDto(tagService.findTagById(id)));
    }
}
