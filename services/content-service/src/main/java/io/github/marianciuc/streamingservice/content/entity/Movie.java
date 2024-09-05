package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "movies")
public class Movie extends BaseEntity{

    @OneToOne(mappedBy = "movie")
    private Content content;

    @Column(name = "duration")
    private int duration;

    @Column(name = "master_playlist_url")
    private String masterPlaylistUrl;

    @Column(name = "master_playlist_id")
    private UUID masterPlaylistId;
}
