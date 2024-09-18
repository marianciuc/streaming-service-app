/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkStateServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadNotInitializedException;
import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadTimeoutException;
import io.github.marianciuc.streamingservice.media.services.impl.ChunkStateServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChunkStateServiceTest {


    @Mock
    private RedisTemplate<String, Boolean[]> redisTemplate;

    @Mock
    private ValueOperations<String, Boolean[]> valueOperations;
    @InjectMocks
    private ChunkStateServiceImpl chunkStateService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createChunkUploadStatus_ShouldSucceed_ForValidInput(){
        UUID fileId = UUID.randomUUID();
        int totalChunks = 5;
        String expectedKey = "chunk_upload::" + fileId;
        Boolean[] expectedChunkStatus = {false, false, false, false, false};

        chunkStateService.createChunkUploadStatus(fileId, totalChunks);

        verify(valueOperations).set(expectedKey, expectedChunkStatus, 1800L, TimeUnit.SECONDS);
    }

    @Test
    void updateChunkUploadStatus_ShouldSucceed_ForValidInput() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        Boolean[] chunkStatus = {false, false, false, false, false};
        when(valueOperations.get(key)).thenReturn(chunkStatus);

        chunkStateService.updateChunkUploadStatus(fileId, 2, chunkStatus.length);

        assertTrue(chunkStatus[1]);
        verify(valueOperations).set(key, chunkStatus, 1800L, TimeUnit.SECONDS);
    }

    @Test
    void updateChunkUploadStatus_ShouldThrowException_ForUninitializedStatus() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        when(valueOperations.get(key)).thenReturn(null);

        assertThrows(ChunkUploadNotInitializedException.class,
                () -> chunkStateService.updateChunkUploadStatus(fileId, 2, 5));
    }

    @Test
    void updateChunkUploadStatus_ShouldThrowException_ForInvalidChunkIndex() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        Boolean[] chunkStatus = {false, false, false, false, false};
        when(valueOperations.get(key)).thenReturn(chunkStatus);

        assertThrows(IllegalArgumentException.class,
                () -> chunkStateService.updateChunkUploadStatus(fileId, -1, chunkStatus.length));

        assertThrows(ChunkUploadTimeoutException.class,
                () -> chunkStateService.updateChunkUploadStatus(fileId, 7, chunkStatus.length));
    }

    @Test
    void isUploadComplete_ShouldReturnTrue_WhenAllChunksUploaded() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        Boolean[] chunkStatus = {true, true, true, true, true};
        when(redisTemplate.opsForValue().get(key)).thenReturn(chunkStatus);

        assertTrue(chunkStateService.isUploadComplete(fileId));
    }

    @Test
    void isUploadComplete_ShouldReturnFalse_WhenNotAllChunksUploaded() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        Boolean[] chunkStatus = {true, true, false, true, true};
        when(valueOperations.get(key)).thenReturn(chunkStatus);

        assertFalse(chunkStateService.isUploadComplete(fileId));
    }

    @Test
    void isUploadComplete_ShouldThrowException_ForUninitializedStatus() {
        UUID fileId = UUID.randomUUID();
        String key = "chunk_upload::" + fileId.toString();
        when(valueOperations.get(key)).thenReturn(null);

        assertThrows(ChunkUploadNotInitializedException.class,
                () -> chunkStateService.isUploadComplete(fileId));
    }

    @Test
    void createChunkUploadStatus_ShouldThrowException_ForInvalidTotalChunks() {
        UUID fileId = UUID.randomUUID();
        final int invalidTotalChunks = 0;

        assertThrows(IllegalArgumentException.class,
                () -> chunkStateService.createChunkUploadStatus(fileId, invalidTotalChunks),
                "Total chunks must be greater than 0"
        );
    }

}