/*
 * Project: STREAMING SERVICE APP
 * File: ChunkStateServiceImpl.java
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadNotInitializedException;
import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadTimeoutException;
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

    private static final long UPLOAD_TIMEOUT_SECONDS = 1800L;
    private static final String CHUNK_UPLOAD_PREFIX = "chunk_upload::";

    private final RedisTemplate<String, Boolean[]> redisTemplate;

    @Override
    public void updateChunkUploadStatus(UUID fileId, int chunkNumber, int totalChunks) {
        String key = generateRedisKey(fileId);
        Boolean[] chunkStatus = getChunkStatusFromRedis(key);

        if (chunkNumber - 1 >= totalChunks) {
            throw new ChunkUploadTimeoutException("Chunk number exceeds total chunks for key: " + key);
        } if (chunkNumber < 0) {
            throw new IllegalArgumentException("Chunk number must be greater than 0");
        }

        chunkStatus[chunkNumber - 1] = true;
        storeChunkStatusInRedis(key, chunkStatus);
    }

    @Override
    public void deleteChunkUploadStatus(UUID fileId) {
        redisTemplate.delete(generateRedisKey(fileId));
    }

    @Override
    public void createChunkUploadStatus(UUID fileId, int totalChunks) {
        if (totalChunks < 1) throw new IllegalArgumentException("Total chunks must be greater than 0");
        String key = generateRedisKey(fileId);
        Boolean[] chunkStatus = initializeChunkStatus(totalChunks);
        storeChunkStatusInRedis(key, chunkStatus);
    }

    private String generateRedisKey(UUID fileId) {
        return CHUNK_UPLOAD_PREFIX + fileId;
    }

    private Boolean[] getChunkStatusFromRedis(String key) {
        Boolean[] chunkStatus = redisTemplate.opsForValue().get(key);

        if (chunkStatus == null) {
            throw new ChunkUploadNotInitializedException("Upload status not initialized for key: " + key);
        }

        return chunkStatus;
    }

    private void storeChunkStatusInRedis(String key, Boolean[] chunkStatus) {
        redisTemplate.opsForValue().set(key, chunkStatus, UPLOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    private Boolean[] initializeChunkStatus(int totalChunks) {
        Boolean[] chunkStatus = new Boolean[totalChunks];
        Arrays.fill(chunkStatus, false);
        return chunkStatus;
    }

    public boolean isUploadComplete(UUID fileId) {
        return Arrays.stream(getChunkStatusFromRedis(generateRedisKey(fileId))).allMatch(Boolean::booleanValue);
    }
}