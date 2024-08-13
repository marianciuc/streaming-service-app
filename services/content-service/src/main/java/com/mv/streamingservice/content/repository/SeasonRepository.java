package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeasonRepository extends JpaRepository<Season, UUID> {
    Optional<Season> findAllByContentId(UUID contentId);
}
