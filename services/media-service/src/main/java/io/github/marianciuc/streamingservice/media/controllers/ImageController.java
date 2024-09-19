package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import io.github.marianciuc.streamingservice.media.services.ImageService;
import io.github.marianciuc.streamingservice.media.validation.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * REST controller for managing image-related operations.
 */
@RestController
@RequestMapping("/api/v1/media/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Retrieves an image by its ID.
     *
     * @param id The UUID of the image.
     * @return A ResponseEntity containing a ByteArrayResource with the image data and appropriate headers.
     */
    @GetMapping("/{picture-id}")
    public ResponseEntity<ByteArrayResource> get(@PathVariable("picture-id") UUID id) {
        ImageDto imageDto = imageService.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageDto.contentType()))
                .body(imageDto.byteArrayResource());
    }

    /**
     * Uploads a new image file.
     *
     * @param file The image file to upload.
     * @param authentication The authentication information of the user uploading the image.
     * @return ResponseEntity containing the UUID of the uploaded image.
     */
    @PostMapping("/upload")
    public ResponseEntity<UUID> upload(@RequestParam(value = "file") @ImageFile MultipartFile file) {
        return ResponseEntity.ok(imageService.upload(file));
    }

    /**
     * Deletes an image by its ID.
     *
     * @param id The UUID of the image to delete.
     * @return ResponseEntity with no content upon successful deletion.
     */
    @DeleteMapping("/{picture-id}")
    public ResponseEntity<Void> delete(@PathVariable("picture-id") UUID id) {
        imageService.delete(id);
        return ResponseEntity.ok().build();
    }
}
