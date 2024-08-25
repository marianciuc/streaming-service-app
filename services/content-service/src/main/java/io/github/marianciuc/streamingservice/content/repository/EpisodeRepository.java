package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EpisodeRepository extends JpaRepository<Episode, UUID> {
}
