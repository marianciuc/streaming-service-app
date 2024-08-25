package io.github.marianciuc.streamingservice.media.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "videos")
public class Video extends Media{

    @Column(name = "is_movie")
    private boolean isMovie;

    @Column(name = "content_media_id")
    private UUID contentMediaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resolution")
    private Resolution resolution;

}
