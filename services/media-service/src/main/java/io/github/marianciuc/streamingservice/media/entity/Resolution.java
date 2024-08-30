package io.github.marianciuc.streamingservice.media.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


/**
 * An enumeration representing different resolutions of media files.
 */
@Data
@Entity
@Table(name = "resolutions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "height")
    private int height;

    @Column(name = "bitrate")
    private int bitrate;

    @OneToMany(mappedBy = "resolution")
    private List<Media> mediaList;
}