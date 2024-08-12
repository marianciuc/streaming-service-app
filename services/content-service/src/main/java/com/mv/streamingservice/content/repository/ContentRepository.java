package com.mv.streamingservice.content.repository;

import com.mv.streamingservice.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {
}
