package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
