/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    ImageDto getImage(UUID id);

    UUID uploadImage(MultipartFile file, Authentication authentication);

    void deleteImage(UUID id);

}
