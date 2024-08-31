/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileValidator.java
 *
 */

package io.github.marianciuc.streamingservice.media.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class VideoFileValidator implements ConstraintValidator<VideoFile, MultipartFile> {

    private Tika tika = new Tika();

    private static final String[] VIDEO_MIME_TYPES = {
            "video/mp4",
            "video/x-msvideo",
            "video/x-matroska",
            "video/x-flv",
            "video/webm",
            "video/ogg"
    };

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        try {
            String mimeType = tika.detect(file.getInputStream());
            for (String videoMimeType : VIDEO_MIME_TYPES) {
                if (videoMimeType.equalsIgnoreCase(mimeType)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
