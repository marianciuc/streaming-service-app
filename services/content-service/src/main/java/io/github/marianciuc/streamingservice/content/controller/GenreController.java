package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.GenreDto;
import io.github.marianciuc.streamingservice.content.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<UUID> createGenre(@RequestBody GenreDto request) {
        return ResponseEntity.ok(genreService.createGenre(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGenre(@PathVariable("id") UUID id, @RequestBody GenreDto request) {
        genreService.updateGenre(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") UUID id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }
}
