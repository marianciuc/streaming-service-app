/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRateService.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services;

import io.github.marianciuc.streamingservice.comments.entity.CommentRateType;

import java.util.UUID;

/**
 * Service for rating comments.
 * @see CommentRateType
 * @see io.github.marianciuc.streamingservice.comments.entity.CommentRate
 * @since 1.0
 */
public interface CommentsRateService {


    /**
     * Rate comment by id and rate type (like/dislike).
     * If comment is already rated by user, the rate will be updated.
     * if rate type is the same, the rate will be removed.
     * User cant rate his own comment.
     * @param id comment id
     * @param rateType rate type
     * @see CommentRateType
     */
    void rateComment(UUID id, CommentRateType rateType);
}
