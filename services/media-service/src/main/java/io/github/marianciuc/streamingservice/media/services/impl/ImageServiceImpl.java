/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.jwtsecurity.entity.JwtUser;
import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import io.github.marianciuc.streamingservice.media.entity.Media;
import io.github.marianciuc.streamingservice.media.entity.MediaType;
import io.github.marianciuc.streamingservice.media.entity.RecordStatus;
import io.github.marianciuc.streamingservice.media.exceptions.ForbiddenException;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.repository.MediaRepository;
import io.github.marianciuc.streamingservice.media.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String FORBIDDEN_MSG = "You are not authorized to upload videos.";
    private static final String IMAGE_NOT_FOUND_MSG = "Image not found";
    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String INTERRUPTED_EXCEPTION_MSG = "The compression operation was interrupted.";
    private static final String COMPRESSION_ERROR_MSG = "Error occurred during compression.";
    private static final String FFMPEG_COMMAND = "ffmpeg";
    private static final String CODEC = "libaom-av1";

    private final UserService userService;
    private final MediaRepository repository;

    @Override
    public UUID uploadImage(MultipartFile file) {
        try {
            Media media = Media.builder()
                    .authorId(userService.getUser().getId())
                    .data(file.getBytes())
                    .contentType(file.getContentType())
                    .mediaType(MediaType.IMAGE)
                    .recordStatus(RecordStatus.ACTIVE)
                    .contentLength(file.getSize())
                    .build();
            return repository.save(media).getId();
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION_MSG, e);
        }
    }

    @Override
    public ImageDto getImage(UUID id) {
        Media media = repository.findByIdAndMediaType(id, MediaType.IMAGE).orElseThrow(() -> new NotFoundException(IMAGE_NOT_FOUND_MSG));
        return new ImageDto(new ByteArrayResource(media.getData()), media.getContentType());
    }


    @Override
    public void deleteImage(UUID id) {
        JwtUser jwtUser = (JwtUser) userService.getUser();
        Media media = repository.findById(id).orElseThrow(() -> new NotFoundException(IMAGE_NOT_FOUND_MSG));

        if (jwtUser.isService() || !(jwtUser.getRole().equals("ROLE_ADMIN") || jwtUser.getRole().equals("ROLE_MODERATOR") || jwtUser.getId().equals(media.getAuthorId()))) {
            throw new ForbiddenException(FORBIDDEN_MSG);
        }

        media.setRecordStatus(RecordStatus.DELETED);
        repository.save(media);
    }
}