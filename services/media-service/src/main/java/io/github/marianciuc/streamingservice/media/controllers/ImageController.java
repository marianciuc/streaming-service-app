package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import io.github.marianciuc.streamingservice.media.dto.ResourceDto;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.services.ImageService;
import io.github.marianciuc.streamingservice.media.validation.ImageFile;
import io.github.marianciuc.streamingservice.media.validation.VideoFile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{picture-id}")
    public ResponseEntity<ByteArrayResource> get(@RequestParam("picture-id") UUID id) {
        ImageDto imageDto = imageService.getImage(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageDto.contentType()))
                .body(imageDto.byteArrayResource());
    }

    @PostMapping("/upload")
    public ResponseEntity<UUID> upload(@RequestParam(value = "file") @ImageFile MultipartFile file, Authentication authentication) {
        return ResponseEntity.ok(imageService.uploadImage(file, authentication));

    }

    @DeleteMapping("/{picture-id}")
    public ResponseEntity<Void> delete(@PathVariable("picture-id") UUID id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
}
