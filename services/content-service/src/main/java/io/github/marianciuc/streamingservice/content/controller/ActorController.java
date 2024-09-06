/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ActorController.java
 *
 */

package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.PersonDto;
import io.github.marianciuc.streamingservice.content.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<UUID> addActor(@Valid @RequestBody PersonDto request) {
        return ResponseEntity.ok(actorService.createActor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateActor(@PathVariable UUID  id, @RequestBody PersonDto request) {
        actorService.updateActor(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable UUID id) {
        actorService.deleteActor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getActor(@PathVariable UUID id) {
        return ResponseEntity.ok(actorService.findActorById(id));
    }
}
