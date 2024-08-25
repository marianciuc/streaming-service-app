package io.github.marianciuc.streamingservice.content.entity;

import io.github.marianciuc.streamingservice.content.enums.Resolution;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media_links", indexes = {
        @Index(name = "index_media_links_movie_id", columnList = "movie_id"),
        @Index(name = "index_media_links_ep_on", columnList = "episode_id")
})
public class MediaLink extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(name = "resolution", nullable = false)
    private Resolution resolution;

    @Column(name = "link", nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;
}