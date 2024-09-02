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

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChunkStateServiceImpl implements ChunkStateService {

    private RedisTemplate<String, Boolean[]> redisTemplate;

    private static final String CHUNK_UPLOAD_PREFIX = "chunk_upload::";

    @Override
    public void updateChunkUploadStatus(UUID fileId, int chunkNumber, int totalChunks) {
        String key = CHUNK_UPLOAD_PREFIX + fileId;
        ValueOperations<String, Boolean[]> ops = redisTemplate.opsForValue();
        Boolean[] chunkStatus = ops.get(key);

        if (chunkStatus == null) {
            chunkStatus = new Boolean[totalChunks];
        }

        chunkStatus[chunkNumber - 1] = true;
        ops.set(key, chunkStatus, 1, TimeUnit.DAYS);
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
}
