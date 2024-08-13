package com.mv.streamingservice.content.controller;

import com.mv.streamingservice.content.dto.request.GenreRequest;
import com.mv.streamingservice.content.dto.response.GenreResponse;
import com.mv.streamingservice.content.dto.response.PaginationResponse;
import com.mv.streamingservice.content.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    public final GenreService genreService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GenreResponse>> getAllGenres(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(genreService.findAll(name, page, size));
    }

    @GetMapping("/{genre-id}")
    public ResponseEntity<GenreResponse> findGenreById(@RequestParam(name = "genre-id") UUID id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UUID> createGenre(@RequestBody @Valid GenreRequest genreRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.createGenre(genreRequest));
    }

    @PutMapping("/{genre-id}")
    public ResponseEntity<UUID> updateGenreById(@PathVariable("genre-id") UUID id, @RequestBody GenreRequest genreRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.updateGenre(id, genreRequest));
    }

    @DeleteMapping("/{genre-id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable("genre-id") UUID id) {
        genreService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
