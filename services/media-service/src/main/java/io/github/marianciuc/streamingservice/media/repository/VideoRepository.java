package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
}
