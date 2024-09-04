/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoUploadingStatusRepository.java
 *
 */

package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.VideoUploadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoUploadingStatusRepository extends JpaRepository<VideoUploadingStatus, UUID> {
}
