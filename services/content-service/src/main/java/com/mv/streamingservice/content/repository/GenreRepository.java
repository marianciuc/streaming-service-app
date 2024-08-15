package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Page<Genre> findByNameContainingIgnoreCase(String name, Pageable pageable);
}