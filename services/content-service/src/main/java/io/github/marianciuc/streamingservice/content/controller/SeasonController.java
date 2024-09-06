/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SeasonController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.SeasonDto;
import io.github.marianciuc.streamingservice.content.service.SeasonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/api/v1/seasons")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @PostMapping
    public ResponseEntity<UUID> createSeason(@RequestBody @Valid SeasonDto request) {
        return ResponseEntity.ok(seasonService.createSeason(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(seasonService.findSeason(id));
    }

    @GetMapping("/content/{content-id}/season/{season-number}")
    public ResponseEntity<SeasonDto> getSeasonByParams(
            @PathVariable("content-id") UUID contentId,
            @PathVariable("season-number") Integer seasonNumber) {
        return ResponseEntity.ok(seasonService.findSeasonByParams(contentId, seasonNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSeason(@PathVariable("id") UUID id, @RequestBody @Valid SeasonDto request) {
        seasonService.updateSeason(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable("id") UUID id) {
        seasonService.deleteSeason(id);
        return ResponseEntity.ok().build();
    }
}
