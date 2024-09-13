/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/rates")
public class RateController {

    @PostMapping("/rate/{contentId}")
    public ResponseEntity<Void> rateContent(@PathVariable UUID contentId, @RequestParam(value = "rate", required = true) Integer rate) {
        return ResponseEntity.ok().build();
    }


}
