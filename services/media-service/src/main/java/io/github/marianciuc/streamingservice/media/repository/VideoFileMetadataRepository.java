package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoFileMetadataRepository extends JpaRepository<VideoFileMetadata, UUID> {

}
