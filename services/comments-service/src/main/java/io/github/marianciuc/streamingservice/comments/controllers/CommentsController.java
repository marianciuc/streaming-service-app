/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewController.java
 *
 */

package io.github.marianciuc.streamingservice.comments.controllers;

import io.github.marianciuc.streamingservice.comments.dto.requests.CreateCommentRequest;
import io.github.marianciuc.streamingservice.comments.dto.response.CommentsResponse;
import io.github.marianciuc.streamingservice.comments.services.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentService;

    @PostMapping
    public ResponseEntity<CommentsResponse> addComment(@Validated @RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(this.commentService.add(request));
    }
}
