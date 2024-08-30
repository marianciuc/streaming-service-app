/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionController.java
 *
 */

package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media/resolutions")
@RequiredArgsConstructor
public class ResolutionController {

    private final ResolutionService resolutionService;

    @GetMapping
    private ResponseEntity<ResolutionDto> getResolution(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(resolutionService.getResolutionById(id));
    }

    @PostMapping
    private ResponseEntity<ResolutionDto> createResolution(@RequestBody @Valid ResolutionDto request) {
        return ResponseEntity.ok(resolutionService.createResolution(request));
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteResolution(@RequestParam("id") UUID id) {
        resolutionService.deleteResolution(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<ResolutionDto> updateResolution(@RequestBody @Valid ResolutionDto request) {
        return ResponseEntity.ok(resolutionService.updateResolution(request));
    }

    @GetMapping
    private ResponseEntity<List<ResolutionDto>> getAllResolutions() {
        return ResponseEntity.ok(resolutionService.getAllResolutions());
    }
}
