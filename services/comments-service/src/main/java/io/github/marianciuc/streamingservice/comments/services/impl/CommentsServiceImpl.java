/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CommentsServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services.impl;

import io.github.marianciuc.streamingservice.comments.clients.ContentClient;
import io.github.marianciuc.streamingservice.comments.clients.CustomerClient;
import io.github.marianciuc.streamingservice.comments.dto.CustomerDto;
import io.github.marianciuc.streamingservice.comments.dto.requests.CreateCommentRequest;
import io.github.marianciuc.streamingservice.comments.dto.requests.CreateReplyRequest;
import io.github.marianciuc.streamingservice.comments.dto.response.CommentsResponse;
import io.github.marianciuc.streamingservice.comments.dto.response.PaginationResponse;
import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.entity.ModerationStatus;
import io.github.marianciuc.streamingservice.comments.kafka.KafkaMessageModerationProducer;
import io.github.marianciuc.streamingservice.comments.kafka.messages.ModerationMessage;
import io.github.marianciuc.streamingservice.comments.repositories.CommentsRepository;
import io.github.marianciuc.streamingservice.comments.security.services.UserService;
import io.github.marianciuc.streamingservice.comments.services.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository repository;
    private final ContentClient contentClient;
    private final UserService userService;
    private final CustomerClient customerClient;
    private final KafkaMessageModerationProducer kafkaMessageModerationProducer;


    @Override
    public CommentsResponse add(CreateCommentRequest commentDto) {
        UUID userId = userService.extractUserIdFromAuth();

        Boolean contentExists = this.contentClient.existsById(commentDto.contentId());
        if (contentExists == null || !contentExists) {
            throw new IllegalArgumentException("Content does not exist");
        }

        Comment comment = Comment.builder()
                .contentId(commentDto.contentId())
                .userId(userId)
                .content(commentDto.content())
                .moderationStatus(ModerationStatus.PENDING)
                .rating(0)
                .isDeleted(false)
                .replies(new ArrayList<>())
                .parentComment(null)
                .build();
        comment = this.repository.save(comment);
        // TODO send notification to parent comment owner
        // TODO send notification to moderation service
        return this.toDtoWithUserDetails(comment);
    }

    @Override
    public Comment find(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    @Override
    public CommentsResponse reply(UUID id, CreateReplyRequest request) {

        Comment parentComment = this.find(id);
        if (parentComment.getParentComment() != null) {
            throw new IllegalArgumentException("Cannot reply to a reply");
        }

        UUID userId = userService.extractUserIdFromAuth();
        Comment comment = Comment.builder()
                .contentId(parentComment.getContentId())
                .userId(userId)
                .content(parentComment.getContent())
                .moderationStatus(ModerationStatus.PENDING)
                .rating(0)
                .isDeleted(false)
                .replies(new ArrayList<>())
                .parentComment(parentComment)
                .build();
        comment = this.repository.save(comment);
        // TODO send notification to parent comment owner
        // TODO send notification to moderation service
        return this.toDtoWithUserDetails(comment);
    }

    @Override
    public void delete(UUID id) {
        Comment comment = this.find(id);

        if (this.userService.hasAdminRoles() || comment.getUserId().equals(this.userService.extractUserIdFromAuth())) {
            this.repository.deleteAll(comment.getReplies());
            this.repository.delete(comment);
        }
    }

    @Override
    public PaginationResponse<CommentsResponse> findByFilter(UUID contentId, Boolean approved, Integer page, Integer size) {
        ModerationStatus status = approved != null ? (approved ? ModerationStatus.APPROVED : ModerationStatus.REJECTED) : ModerationStatus.REJECTED;
        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> commentPage = this.repository.findByContentIdAndModerationStatus(contentId, status, pageable);
        return new PaginationResponse<>(
                commentPage.getTotalPages(),
                commentPage.getNumber(),
                commentPage.getSize(),
                commentPage.stream()
                        .map(this::toDtoWithUserDetails)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void updateRating(Comment comment, Integer addedRating) {
        comment.setRating(comment.getRating() + addedRating);
        this.repository.save(comment);
    }

    @Override
    public void updateModerationStatus(ModerationMessage moderationMessage) {
        Comment comment = this.find(moderationMessage.commentId());
        comment.setModerationStatus(moderationMessage.moderationStatus());
        this.repository.save(comment);
    }

    private CommentsResponse toDtoWithUserDetails(Comment comment) {
        Map<UUID, Optional<CustomerDto>> userCache = new HashMap<>();
        return toDtoRecursive(comment, userCache);
    }

    private CommentsResponse toDtoRecursive(Comment comment, Map<UUID, Optional<CustomerDto>> userCache) {
        CustomerDto customerDto = userCache.computeIfAbsent(comment.getUserId(), this.customerClient::getCustomerById)
                .orElseThrow(() -> new IllegalArgumentException("User not found for comment with ID: " + comment.getId()));

        List<CommentsResponse> replies = comment.getReplies().stream()
                .map(reply -> toDtoRecursive(reply, userCache)).toList();

        return new CommentsResponse(
                comment.getId(),
                comment.getContent(),
                comment.getReplies().stream().map(reply -> toDtoRecursive(reply, userCache)).toList(),
                customerDto.id(),
                customerDto.username(),
                customerDto.profilePicture()
        );
    }
}
