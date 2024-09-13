/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewService.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services;

import io.github.marianciuc.streamingservice.comments.dto.requests.CreateCommentRequest;
import io.github.marianciuc.streamingservice.comments.dto.requests.CreateReplyRequest;
import io.github.marianciuc.streamingservice.comments.dto.response.CommentsResponse;
import io.github.marianciuc.streamingservice.comments.dto.response.PaginationResponse;
import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.kafka.messages.ModerationMessage;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

/**
 * CommentsService interface for comments operations
 * @see Comment
 */
public interface CommentsService {

    /**
     * Add a new comment to a content
     * @param request CreateCommentRequest
     * @return CommentResponse with included customer details (profile picture, name, etc.)
     * @throws IllegalArgumentException if content not found or content is not published
     * @throws AccessDeniedException if user is not authenticated
     * @see CommentsResponse
     * @see CreateCommentRequest
     */
    CommentsResponse add(CreateCommentRequest request);


    /**
     * Find a comment by UUID
     * @param id the UUID of the comment
     * @return comment entity with replies or parent comment
     * @throws IllegalArgumentException if comment not found
     */
    Comment find(UUID id);

    /**
     * Add a reply to a comment
     * @param id the UUID of the parent comment
     * @param request CreateReplyRequest
     * @return CommentDto with included customer details (profile picture, name, etc.)
     * @throws IllegalArgumentException if parent comment not found or parent comment is reply to another comment
     * @see CreateReplyRequest
     */
    CommentsResponse reply(UUID id, CreateReplyRequest request);

    /**
     * Delete a comment and replies to this comment by UUID
     * @param id the UUID of the comment
     * @throws IllegalArgumentException if comment not found
     */
    void delete(UUID id);

    /**
     * Update moderation status of a comment
     * @param moderationMessage ModerationMessage
     * @throws IllegalArgumentException if comment not found
     * @see ModerationMessage
     */
    void updateModerationStatus(ModerationMessage moderationMessage);

    /**
     * Find comments by filter
     * @param contentId the UUID of the content
     * @param approved the approval status of the comment
     * @param page the page number
     * @param size the size of the page
     * @return List of CommentDto with included customer details (profile picture, name, etc.)
     */
    PaginationResponse<CommentsResponse> findByFilter(UUID contentId, Boolean approved, Integer page, Integer size);

    /**
     * Update rating of a comment. If addedRating is negative, the rating will be decreased
     * @param comment Comment entity
     * @param addedRating the rating to be added to the comment rating
     */
    void updateRating(Comment comment, Integer addedRating);
}
