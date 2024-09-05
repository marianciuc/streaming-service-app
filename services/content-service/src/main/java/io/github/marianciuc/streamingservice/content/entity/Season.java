package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "seasons", indexes = {
        @Index(name = "index_season_number", columnList = "number"),
        @Index(name = "index_season_title", columnList = "title")
})
public class Season extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    @NotNull
    private Content content;

    @Column(name = "number", nullable = false)
    @NotNull
    private Integer number;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Builder.Default
    private String title = "Untitled Season";

    @Column(name = "season_release_date", nullable = false)
    @NotNull
    @Builder.Default
    private LocalDate releaseDate = LocalDate.now();

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodes;
}
