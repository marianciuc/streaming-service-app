/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ModerationMessage.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "moderation_messages")
public class ModerationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "message_id")
    private UUID messageID;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ModerationStatus status;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "moderator_id")
    private UUID moderatorID;

    @Column(name = "user_id")
    private UUID userID;

    @Column(name = "content")
    private String content;
}
