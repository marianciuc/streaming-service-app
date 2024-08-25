package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.exceptions.InvalidFileType;
import io.github.marianciuc.streamingservice.media.services.MediaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/{picture-id}")
    public ResponseEntity<ByteArrayResource> get(@RequestParam("picture-id") UUID id) {
        return ResponseEntity.ok(new ByteArrayResource(mediaService.getPicture(id)));
    }

    @GetMapping(value = "/video/{video-id}", produces = "video/mp4")
    public ResponseEntity<Resource> streamVideo(@PathVariable("video-id") UUID videoId, HttpServletRequest request) {
        ResourceResponse videoResponse = mediaService.getVideoResource(videoId, request);

        return ResponseEntity.status(videoResponse.status())
                .contentType(MediaType.parseMediaType(videoResponse.contentType()))
                .header(HttpHeaders.CONTENT_LENGTH, videoResponse.rangeLength())
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + videoResponse.rangeStart() + "-" + videoResponse.rangeEnd() + "/" + videoResponse.fileLength())
                .body(new InputStreamResource(videoResponse.resource()));
    }

    @PostMapping("/video")
    public ResponseEntity<UUID> upload(
            @RequestParam(value = "isEpisode") boolean isEpisode,
            @RequestParam(value = "media-id") UUID mediaId,
            @RequestParam(value = "source-quality") Resolution sourseResolution,
            @RequestParam(value = "file") MultipartFile file
    ) {
        mediaService.uploadVideo(isEpisode, mediaId, file, sourseResolution);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<UUID> upload(@RequestParam(value = "file") MultipartFile file) {
        String contentType = file.getContentType();

        if(contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE) || contentType.equals(MediaType.IMAGE_GIF_VALUE)) {
            mediaService.uploadPicture(file);
            return ResponseEntity.ok().build();
        } else {
            throw new InvalidFileType("Invalid file type. Only JPEG, PNG and GIF images are allowed.");
        }
    }
}
