package io.github.marianciuc.streamingservice.content.controller;

import io.github.marianciuc.streamingservice.content.dto.ContentDto;
import io.github.marianciuc.streamingservice.content.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("/{id}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(contentService.getContent(id));
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<List<ContentDto>>> findAllContents(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize,
            @RequestParam(value = "orderByDateDesk", required = false) Boolean orderByDateDesk,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "type", required = false, defaultValue = "") ContentType type,
            @RequestParam(value = "directorId", required = false, defaultValue = "") UUID directorId,
            @RequestParam(value = "actorId", required = false, defaultValue = "") UUID actorId,
            @RequestParam(value = "ageRating", required = false, defaultValue = "") String ageRating,
            @RequestParam(value = "releaseDateYear", required = false, defaultValue = "") String releaseDateYear,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "ACTIVE") RecordStatus recordStatus,
            @RequestParam(value = "genreId", required = false, defaultValue = "") UUID genreId
    ) {
        return ResponseEntity.ok(
                contentService.getAllContentByFilters(
                        title,
                        genreId,
                        directorId,
                        page,
                        pageSize,
                        actorId,
                        ageRating,
                        releaseDateYear,
                        type,
                        recordStatus,
                        orderByDateDesk
                ));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UUID> createContent(@RequestBody @Validated ContentDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createContent(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> updateContent(@PathVariable("id") UUID id, @RequestBody @Validated ContentDto request) {
        contentService.updateContent(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteContent(@PathVariable("id") UUID id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok().build();
    }
}
