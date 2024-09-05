package io.github.marianciuc.streamingservice.content.entity;

import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
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

    @Column(name = "master_playlist_id")
    UUID masterPlaylistId;
}
