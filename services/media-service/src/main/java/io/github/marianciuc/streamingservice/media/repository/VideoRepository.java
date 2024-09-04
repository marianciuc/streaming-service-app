/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoRepository.java
 *
 */

package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    Optional<Video> findVideoById(UUID videoId);

    @Modifying
    @Query("UPDATE Video v SET v.resolutionCounter = v.resolutionCounter + :incrementValue WHERE v.id = :videoId")
    void incrementResolutionCounter(@Param("videoId") UUID videoId, @Param("incrementValue") int incrementValue);
}
