/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRepository.java
 *
 */

package io.github.marianciuc.streamingservice.comments.repositories;

import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.entity.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByContentIdAndModerationStatus(UUID contentId, ModerationStatus status, Pageable pageable);
}
