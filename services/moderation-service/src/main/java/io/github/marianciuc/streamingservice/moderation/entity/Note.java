/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Notes.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255, name = "note")
    private String note;

    @Column(nullable = false, name = "author_id")
    private UUID authorId;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
