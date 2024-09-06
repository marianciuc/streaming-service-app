/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: DirectorsController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;


import io.github.marianciuc.streamingservice.content.dto.PersonDto;
import io.github.marianciuc.streamingservice.content.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/api/v1/directors")
@RequiredArgsConstructor
public class DirectorsController {

    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<UUID> createDirector(@Valid @RequestBody PersonDto request) {
        return ResponseEntity.ok(directorService.createDirector(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getDirectorById(@PathVariable UUID id) {
        return ResponseEntity.ok(directorService.findDirectorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDirector(@PathVariable UUID id, @RequestBody PersonDto request) {
        directorService.updateDirector(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable UUID id) {
        directorService.deleteDirector(id);
        return ResponseEntity.ok().build();
    }
}
