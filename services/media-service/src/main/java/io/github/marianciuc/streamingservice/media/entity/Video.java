/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Video.java
 *
 */

package io.github.marianciuc.streamingservice.media.entity;

import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content_id")
    private UUID contentId;

    @Column(name = "content_type")
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Column(name = "processed_resolutions")
    private Integer processedResolutions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VideoStatues status;

    @Column(name = "master_playlist_path")
    private String masterPlaylistPath;

    @OneToMany(mappedBy = "videos", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoFileMetadata> files;

    @OneToMany(mappedBy = "videos", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoUploadingStatus> statuses;
}
