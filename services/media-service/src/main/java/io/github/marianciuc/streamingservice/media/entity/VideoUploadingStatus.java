/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Error.java
 *
 */

package io.github.marianciuc.streamingservice.media.entity;

import io.github.marianciuc.streamingservice.media.enums.StatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video_uploading_statues")
public class VideoUploadingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private StatusType type;
}
