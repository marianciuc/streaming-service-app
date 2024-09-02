package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import io.github.marianciuc.streamingservice.media.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<VideoFileMetadata, UUID> {
    Optional<VideoFileMetadata> findByIdAndMediaType(UUID id, MediaType mediaType);

    List<VideoFileMetadata> findAllByContentId(UUID contentId);
}
