/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryRepository.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.repositories;

import io.github.marianciuc.streamingservice.moderation.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByDeleted(Boolean isDeleted);
}
