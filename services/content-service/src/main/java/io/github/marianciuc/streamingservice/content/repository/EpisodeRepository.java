package io.github.marianciuc.streamingservice.content.repository;

import io.github.marianciuc.streamingservice.content.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EpisodeRepository extends JpaRepository<Episode, UUID> {
}
