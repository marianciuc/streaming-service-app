package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findByContentId(UUID contentId);
}
