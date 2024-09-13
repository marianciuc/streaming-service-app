/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewDto.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto;

import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.entity.ModerationStatus;

import java.util.List;
import java.util.UUID;

public record CommentDto(
    UUID id,
    UUID userId,
    List<CommentDto> replies,
    UUID parent,
    String content,
    ModerationStatus moderationStatus,
    UUID contentId,
    Integer rating
) {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getUserId(),
                comment.getReplies().stream().map(CommentDto::toDto).toList(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null,
                comment.getContent(),
                comment.getModerationStatus(),
                comment.getContentId(),
                comment.getRating()
        );
    }
}
