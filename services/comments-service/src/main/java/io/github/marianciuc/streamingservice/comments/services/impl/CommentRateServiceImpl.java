/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CommentRateServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services.impl;

import io.github.marianciuc.streamingservice.comments.entity.Comment;
import io.github.marianciuc.streamingservice.comments.entity.CommentRate;
import io.github.marianciuc.streamingservice.comments.entity.CommentRateType;
import io.github.marianciuc.streamingservice.comments.repositories.CommentsRateRepository;
import io.github.marianciuc.streamingservice.comments.security.services.UserService;
import io.github.marianciuc.streamingservice.comments.services.CommentsRateService;
import io.github.marianciuc.streamingservice.comments.services.CommentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentRateServiceImpl implements CommentsRateService {

    private final CommentsRateRepository repository;
    private final CommentsService commentsService;
    private final UserService userService;

    @Override
    @Transactional
    public void rateComment(UUID id, CommentRateType rateType) {
        Comment comment = this.commentsService.find(id);
        UUID userId = this.userService.extractUserIdFromAuth();

        if (comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You can't rate your own comment");
        }

        CommentRate commentRate = this.repository.findByCommentAndUserId(comment, userId)
                .orElse(CommentRate.builder()
                        .comment(comment)
                        .userId(userId)
                        .build());

        if (commentRate.getId() != null && commentRate.getReviewRateType() == rateType) {
            this.commentsService.updateRating(comment, -rateType.getValue());
            this.repository.delete(commentRate);
        } else {
            commentRate.setReviewRateType(rateType);
            this.commentsService.updateRating(comment, rateType.getValue());
            this.repository.save(commentRate);
        }
    }
}
