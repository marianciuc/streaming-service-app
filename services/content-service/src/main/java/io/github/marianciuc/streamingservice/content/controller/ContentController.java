package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.request.ContentRequest;
import io.github.marianciuc.streamingservice.content.dto.request.EpisodeRequest;
import io.github.marianciuc.streamingservice.content.dto.request.SeasonRequest;
import io.github.marianciuc.streamingservice.content.dto.response.*;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import io.github.marianciuc.streamingservice.content.service.EpisodeService;
import io.github.marianciuc.streamingservice.content.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final SeasonService seasonService;
    private final EpisodeService episodeService;

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(contentService.findContentById(id));
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<ContentResponse>> findAllContents(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "1") Integer size,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "type", required = false, defaultValue = "") ContentType type,
            @RequestParam(value = "genre-id", required = false, defaultValue = "") UUID genreId
    ) {
        return ResponseEntity.ok(contentService.findContent(page, size, title, type, genreId));
    }

    @PostMapping
    public ResponseEntity<UUID> createContent(@RequestBody @Validated ContentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createContent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateContent(@PathVariable("id") UUID id, @RequestBody @Validated ContentRequest request) {
        return ResponseEntity.ok(contentService.updateContent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable("id") UUID id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{content-id}/movie")
    public ResponseEntity<MovieResponse> getMovieContent(@PathVariable("content-id") UUID id) {
        return ResponseEntity.ok(contentService.getMovieContent(id));
    }

    @GetMapping("/{content-id}/series/seasons")
    public ResponseEntity<List<SeasonResponse>> getSeasonContents(
            @PathVariable("content-id") UUID contentId,
            @RequestParam(value = "season-number", required = false, defaultValue = "") Integer seasonNumber
    ) {
        return ResponseEntity.ok(seasonService.getSeasonsByContentId(contentId, seasonNumber));
    }

    @GetMapping("/{content-id}/series/seasons/{season-number}/episode/{episode-number}")
    public ResponseEntity<EpisodeResponse> getEpisodeContent(
            @PathVariable("content-id") UUID contentId,
            @PathVariable("season-number") Integer seasonNumber,
            @PathVariable("episode-number") Integer episodeNumber
    ) {
        return ResponseEntity.ok(episodeService.findEpisodeByContentIdAndSeasonNumberAndEpisodeNumber(contentId, seasonNumber, episodeNumber));
    }

    @PostMapping("/{content-id}/series/seasons")
    public ResponseEntity<UUID> createSeasonContent(
            @PathVariable("content-id") UUID contentId,
            @RequestBody @Validated SeasonRequest request
    ) {
        return ResponseEntity.ok(seasonService.createSeason(contentId, request));
    }

    @PostMapping("/{content-id}/series/seasons/{season-number}/episode")
    public ResponseEntity<UUID> createEpisodeContent(
            @PathVariable("content-id") UUID contentId,
            @PathVariable("season-number") Integer seasonNumber,
            @RequestBody @Validated EpisodeRequest request
    ) {
        return ResponseEntity.ok(episodeService.createEpisode(contentId, seasonNumber, request));
    }

    @PutMapping("/seasons/{season-id}")
    public ResponseEntity<UUID> updateSeasonContent(
            @PathVariable("season-id") UUID seasonId,
            @RequestBody @Validated SeasonRequest request
    ) {
        return ResponseEntity.ok(seasonService.updateSeason(seasonId, request));
    }

    @PutMapping("/episode/{episode-id}")
    public ResponseEntity<UUID> updateEpisodeContent(
            @PathVariable("episode-id") UUID episodeId,
            @RequestBody @Validated EpisodeRequest request
    ) {
        return ResponseEntity.ok(episodeService.updateEpisode(episodeId, request));
    }

    @DeleteMapping("/episode/{episode-id}")
    public ResponseEntity<Void> deleteEpisodeContent(
            @PathVariable("episode-id") UUID episodeId
    ){
        episodeService.deleteEpisode(episodeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/seasons/{season-id}")
    public ResponseEntity<Void> deleteSeasonContent(
            @PathVariable("season-id") UUID seasonId
    ){
        seasonService.deleteSeason(seasonId);
        return ResponseEntity.ok().build();
    }
}
