package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.MediaLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaFileLinkRepository extends JpaRepository<MediaLink, UUID> {
}
