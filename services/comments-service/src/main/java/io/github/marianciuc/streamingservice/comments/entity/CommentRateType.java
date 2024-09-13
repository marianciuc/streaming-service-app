/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRateType.java
 *
 */

package io.github.marianciuc.streamingservice.comments.entity;

import lombok.Getter;

@Getter
public enum CommentRateType {
    LIKE(-1),
    DISLIKE(1);

    private final int value;

    CommentRateType(int value) {
        this.value = value;
    }
}
