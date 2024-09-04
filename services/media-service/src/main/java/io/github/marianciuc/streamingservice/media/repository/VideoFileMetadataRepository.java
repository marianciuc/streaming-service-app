package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VideoFileMetadataRepository extends JpaRepository<VideoFileMetadata, UUID> {

    Optional<VideoFileMetadata> findByIdAndMediaType(UUID id, MediaType mediaType);
    List<VideoFileMetadata> findAllByContentId(UUID contentId);
}
