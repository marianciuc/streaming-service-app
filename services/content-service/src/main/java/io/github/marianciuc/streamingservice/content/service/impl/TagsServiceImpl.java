/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagsServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.TagDto;
import io.github.marianciuc.streamingservice.content.entity.Tag;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.repository.TagRepository;
import io.github.marianciuc.streamingservice.content.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public UUID createTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        Tag newTag = Tag.builder()
                .name(tag)
                .build();
        return repository.save(newTag).getId();
    }

    @Override
    public void deleteTag(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Tag not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateTag(UUID id, String newTag) {
        if (newTag == null || newTag.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }

        Tag tag = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found"));
        tag.setName(newTag);
        repository.save(tag);
    }

    @Override
    public Tag findTagById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found"));
    }

    @Override
    public List<TagDto> findTagByNamePrefix(String prefix) {
        return repository.findByNameStartingWith(prefix).stream().map(TagDto::toTagDto).toList();
    }
}
