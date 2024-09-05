package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The Episode class represents an episode of a TV series.
 */
@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "episodes")
public class Episode extends BaseEntity{

    /**
     * The season variable represents the season to which an episode belongs.
     */
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    /**
     * The episodeNumber variable represents the episode number of an episode of a TV series.
     * It is mapped to the database column "episode_number".
     * It is a required field and cannot be null.
     */
    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    /**
     * The title variable represents the title of an episode of a TV series.
     * It is mapped to the database column "title".
     * It is a required field and cannot be null.
     */
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "master_playlist_id")
    UUID masterPlaylistId;
}
