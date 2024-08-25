package io.github.marianciuc.streamingservice.content.repository;

import io.github.marianciuc.streamingservice.content.entity.MediaLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaFileLinkRepository extends JpaRepository<MediaLink, UUID> {
}
