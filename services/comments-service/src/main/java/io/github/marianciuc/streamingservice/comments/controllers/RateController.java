/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateController.java
 *
 */

package io.github.marianciuc.streamingservice.comments.controllers;

import io.github.marianciuc.streamingservice.comments.entity.CommentRateType;
import io.github.marianciuc.streamingservice.comments.services.CommentsRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/comments/rates")
@RequiredArgsConstructor
public class RateController {

    private final CommentsRateService service;

    @PostMapping("/{comment-id}/rate")
    public ResponseEntity<Void> rateComment(@PathVariable("comment-id") UUID commentId,
                                            @RequestParam("rate") CommentRateType rate) {
        this.service.rateComment(commentId, rate);
        return ResponseEntity.ok().build();
    }
}
