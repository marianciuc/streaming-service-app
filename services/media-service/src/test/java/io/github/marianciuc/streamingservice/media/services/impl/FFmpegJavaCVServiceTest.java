/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegJavaCVServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.services.VideoStorageService;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class FFmpegJavaCVServiceTest {

    @Mock
    private VideoStorageService videoStorageService;

    @InjectMocks
    private FFmpegJavaCVService ffmpegJavaCVService;

    private File testInputFile;

    @Test
    void compressVideoAndUploadToStorage() throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        ResolutionDto resolutionDto = new ResolutionDto(UUID.randomUUID(), "1920", "1080", 720, 1280, 3000);
        UUID videoId = UUID.randomUUID();
        File tempFile = Files.createTempFile("input", ".mp4").toFile();
        when(videoStorageService.assembleVideo(videoId)).thenReturn(Files.newInputStream(Path.of(tempFile.getAbsolutePath())));
        String result = ffmpegJavaCVService.compressVideoAndUploadToStorage(resolutionDto, videoId);
        assertNotNull(result);
        verify(videoStorageService, atLeastOnce()).uploadFile(anyString(), any(), anyLong(), anyString());
        tempFile.delete();
    }
}