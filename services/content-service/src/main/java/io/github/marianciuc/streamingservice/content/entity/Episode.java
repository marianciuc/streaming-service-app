package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Column(name = "episode_number", nullable = false)
    private Integer number;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "master_playlist_url")
    private String masterPlaylistUrl;

    @Column(name = "master_playlist_id")
    private UUID masterPlaylistId;
}
