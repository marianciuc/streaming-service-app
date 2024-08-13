package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID>, JpaSpecificationExecutor<Content> {
    Page<Content> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Content> findAll(Specification<Content> spec, Pageable pageable);
}
