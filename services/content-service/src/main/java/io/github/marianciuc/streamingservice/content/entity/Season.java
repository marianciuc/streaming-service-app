package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seasons")
@Data
public class Season extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "season_title")
    private String seasonTitle;

    @Column(name = "season_release_date")
    private LocalDateTime seasonReleaseDate;

    @OneToMany(mappedBy = "season")
    private List<Episode> episodes;
}
