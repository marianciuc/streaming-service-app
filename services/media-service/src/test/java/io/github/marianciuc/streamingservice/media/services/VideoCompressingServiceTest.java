/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoCompressingServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.services.impl.FFmpegJavaCVService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoCompressingServiceTest {

    @Mock
    private VideoStorageService videoStorageService;

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private FFmpegJavaCVService videoCompressingService;

    @Mock
    private FFmpegFrameGrabber grabber;

    @Mock
    private FFmpegFrameRecorder recorder;

    @Mock
    private Frame frame;

    @BeforeEach
    void setUp() {
//        videoCompressingService = new FFmpegJavaCVService(videoStorageService, playlistService);
    }

    @Test
    void testExecuteCompression() throws Exception {
        String inputFilePath = "src/test/resources/sample.mp4";
        ResolutionDto resolution = new ResolutionDto(
                UUID.randomUUID(),
                "240p",
                "test-description",
                640,
                240,
                127000
        );
        UUID videoId = UUID.randomUUID();

        try (InputStream inputStream =
                         new File("src/test/resources/test_video.mp4").toURI().toURL().openStream();) {
            StringBuilder playlistBuilder = new StringBuilder();
            when(playlistService.generateResolutionPlaylist()).thenReturn(playlistBuilder);
            when(videoStorageService.assembleVideoTemporaryVideoFile(any(UUID.class))).thenReturn(inputStream);

            when(videoStorageService.uploadVideoSegment(any(ByteArrayOutputStream.class), eq(videoId), eq(resolution), anyInt(), anyString()))
                    .thenReturn("segment0.ts");
            when(playlistService.buildResolutionPlaylist(any(UUID.class), any(StringBuilder.class),
                    any(ResolutionDto.class))).thenReturn("playlist.m3u8");

            String playlist = videoCompressingService.compressVideoAndUploadToStorage(resolution, videoId);

            assertNotNull(playlist);
        }
    }
}