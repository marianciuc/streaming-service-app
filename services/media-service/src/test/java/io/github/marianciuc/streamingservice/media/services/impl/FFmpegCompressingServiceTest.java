/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegCompressingServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FFmpegCompressingServiceTest {

    @Autowired
    FFmpegCompressingService ffmpegCompressingService;

    @Test
    void testCompressVideo() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "videoFile",
                "video.mpg",
                "video/mpg",
                "test video content".getBytes());

        Resolution resolution = Resolution.builder()
                .id(UUID.randomUUID())
                .name("test")
                .description("test resolution")
                .height(720)
                .bitrate(1200)
                .build();

        assertThrows(RuntimeException.class, () -> ffmpegCompressingService.compressVideo(mockMultipartFile, resolution));
    }
}