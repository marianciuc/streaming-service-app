package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Media;
import io.github.marianciuc.streamingservice.media.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    Optional<Media> findByIdAndMediaType(UUID id, MediaType mediaType);

    List<Media> findAllByContentId(UUID contentId);
}
