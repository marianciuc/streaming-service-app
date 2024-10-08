package io.github.marianciuc.streamingservice.content.entity;

import io.github.marianciuc.streamingservice.content.enums.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "content", indexes = {
        @Index(name = "index_content_title", columnList = "title")
})
public class Content extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    private BigDecimal avgRate;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "rating")
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "rate_count")
    private int rateCount = 0;

    @Column(name = "rate_sum")
    private BigDecimal rateSum = BigDecimal.ZERO;

    @ManyToMany
    @JoinTable(
            name = "content_genres",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToMany
    @JoinTable(
            name = "content_tags",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons;

    @ManyToMany
    @JoinTable(
            name = "content_directors",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;

    @ManyToMany
    @JoinTable(
            name = "content_actors",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;


    @OneToMany(mappedBy = "content")
    private List<Rate> rates;
}
