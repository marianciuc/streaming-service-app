/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegJavaCVServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class FFmpegJavaCVServiceTest {
    @Mock
    private MultipartFile multipartFileMock;

    @InjectMocks
    private FFmpegJavaCVService ffmpegJavaCVService;

    private File testInputFile;


    @BeforeEach
    public void setUp() throws IOException {
        testInputFile = new File("src/test/resources/test_video.mp4");
        log.info("{}", testInputFile.exists());
        log.info("{}", testInputFile.getAbsolutePath());
        log.info("{}", testInputFile.length());

        FileInputStream fileInputStream = new FileInputStream(testInputFile);

        when(multipartFileMock.getOriginalFilename()).thenReturn(testInputFile.getName());
        when(multipartFileMock.getSize()).thenReturn(testInputFile.length());
        when(multipartFileMock.getInputStream()).thenReturn(fileInputStream);
        when(multipartFileMock.getContentType()).thenReturn("video/mp4");
        when(multipartFileMock.isEmpty()).thenReturn(false);
    }


    @Test
    public void testCompressVideoWithSuccess() {
        Resolution resolution = Resolution.builder()
                .width(640)
                .height(480)
                .bitrate(400_000)
                .build();

        byte[] compressedVideo = ffmpegJavaCVService.compressVideo(multipartFileMock, resolution);
        assertNotNull(compressedVideo, "The compressed video should not be null.");
    }
}