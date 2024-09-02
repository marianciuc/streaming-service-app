/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ImageRepository.java
 *
 */

package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
