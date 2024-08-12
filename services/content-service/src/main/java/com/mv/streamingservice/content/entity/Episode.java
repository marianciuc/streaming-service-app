package com.mv.streamingservice.content.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "episodes")
public class Episode extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Column(name = "episode_number", nullable = false)
    private int episodeNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "episode")
    private List<MediaLink> mediaLinks;
}
