/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChumkStateServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.services.ChunkStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChunkStateServiceImpl implements ChunkStateService {

    private RedisTemplate<String, Boolean[]> redisTemplate;

    private static final long UPLOAD_TIMEOUT_SECONDS = 1800L;
    private static final String CHUNK_UPLOAD_PREFIX = "chunk_upload::";

    @Override
    public void updateChunkUploadStatus(UUID fileId, int chunkNumber, int totalChunks) {
        String key = CHUNK_UPLOAD_PREFIX + fileId;
        ValueOperations<String, Boolean[]> ops = redisTemplate.opsForValue();
        Boolean[] chunkStatus = ops.get(key);

        if (chunkStatus == null) {
            throw new IllegalStateException("Upload status not initialized for file: " + fileId);
        }

        chunkStatus[chunkNumber - 1] = true;

        ops.set(key, chunkStatus, UPLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        if (isUploadComplete(chunkStatus)) {
            deleteChunkUploadStatus(fileId);
        }
    }

    @Override
    public Boolean[] getChunkUploadStatus(UUID fileId) {
        String key = CHUNK_UPLOAD_PREFIX + fileId;
        ValueOperations<String, Boolean[]> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    @Override
    public void deleteChunkUploadStatus(UUID fileId) {
        String key = CHUNK_UPLOAD_PREFIX + fileId;
        redisTemplate.delete(key);
    }

    @Override
    public void createChunkUploadStatus(UUID fileId, int totalChunks) {
        String key = CHUNK_UPLOAD_PREFIX + fileId;
        Boolean[] chunkStatus = new Boolean[totalChunks];

        Arrays.fill(chunkStatus, false);
        redisTemplate.opsForValue().set(key, chunkStatus, UPLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    private boolean isUploadComplete(Boolean[] chunkStatus) {
        for (Boolean status : chunkStatus) {
            if (!Boolean.TRUE.equals(status)) {
                return false;
            }
        }
        return true;
    }
}
