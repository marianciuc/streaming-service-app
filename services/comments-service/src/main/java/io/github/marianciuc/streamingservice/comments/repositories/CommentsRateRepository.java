/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRateRepository.java
 *
 */

package io.github.marianciuc.streamingservice.comments.repositories;

import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.entity.CommentRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentsRateRepository extends JpaRepository<CommentRate, UUID> {
    Optional<CommentRate> findByCommentAndUserId(Comment comment, UUID userId);
}
