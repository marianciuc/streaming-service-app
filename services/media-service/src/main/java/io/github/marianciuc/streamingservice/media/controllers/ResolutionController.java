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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The ResolutionController class provides RESTful endpoints for managing media resolutions.
 *
 * <br></br>
 * Allowed only users with 'ROLE_SERVICE' or 'ROLE_ADMIN' roles to access the endpoints.
 */
@RestController
@RequestMapping("/api/v1/media/resolutions")
@RequiredArgsConstructor
public class ResolutionController {

    private final ResolutionService resolutionService;

    /**
     * Retrieves the resolution details for a given resolution ID.
     *
     * @param id the unique identifier of the resolution
     * @return a ResponseEntity containing the ResolutionDto for the specified ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE', 'ROLE_ADMIN')")
    private ResponseEntity<ResolutionDto> getResolution(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(resolutionService.getResolutionById(id));
    }

    /**
     * Creates a new resolution based on the provided ResolutionDto.
     *
     * @param request the ResolutionDto containing the resolution details to be created
     * @return a ResponseEntity containing the created ResolutionDto
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE', 'ROLE_ADMIN')")
    private ResponseEntity<ResolutionDto> createResolution(@RequestBody @Valid ResolutionDto request) {
        return ResponseEntity.ok(resolutionService.createResolution(request));
    }

    /**
     * Deletes a resolution specified by the given UUID.
     *
     * @param id the unique identifier of the resolution to be deleted
     * @return a ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE', 'ROLE_ADMIN')")
    private ResponseEntity<Void> deleteResolution(@RequestParam("id") UUID id) {
        resolutionService.deleteResolution(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing resolution based on the provided ResolutionDto.
     *
     * @param request the ResolutionDto containing the resolution details to be updated
     * @return a ResponseEntity containing the updated ResolutionDto
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE', 'ROLE_ADMIN')")
    private ResponseEntity<ResolutionDto> updateResolution(@RequestBody @Valid ResolutionDto request) {
        return ResponseEntity.ok(resolutionService.updateResolution(request));
    }

    /**
     * Retrieves a list of all available resolutions.
     *
     * @return a ResponseEntity containing a List of ResolutionDto objects representing all resolutions
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE', 'ROLE_ADMIN')")
    private ResponseEntity<List<ResolutionDto>> getAllResolutions() {
        return ResponseEntity.ok(resolutionService.getAllResolutions());
    }
}
